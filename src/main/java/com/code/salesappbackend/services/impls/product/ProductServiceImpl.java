package com.code.salesappbackend.services.impls.product;

import com.code.salesappbackend.dtos.requests.product.ProductDetailDto;
import com.code.salesappbackend.dtos.requests.product.ProductDto;
import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.dtos.responses.product.ProductResponse;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.mappers.product.ProductMapper;
import com.code.salesappbackend.models.product.*;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.product.ProductDetailRepository;
import com.code.salesappbackend.repositories.product.ProductImageRepository;
import com.code.salesappbackend.repositories.product.ProductPriceRepository;
import com.code.salesappbackend.repositories.product.ProductRepository;
import com.code.salesappbackend.repositories.customizations.ProductQuery;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.product.ProductRedisService;
import com.code.salesappbackend.services.interfaces.product.ProductService;
import com.code.salesappbackend.utils.S3Upload;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final S3Upload s3Upload;
    private final ProductDetailRepository productDetailRepository;
    private final ProductQuery productQuery;
    private final ProductRedisService productRedisService;



    public ProductServiceImpl(BaseRepository<Product, Long> repository,
                              ProductMapper productMapper,
                              ProductRepository productRepository,
                              ProductImageRepository productImageRepository,
                              S3Upload s3Upload,
                              ProductDetailRepository productDetailRepository,
                              ProductQuery productQuery,
                              ProductRedisService productRedisService
                             ) {
        super(repository, Product.class);
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.s3Upload = s3Upload;
        this.productDetailRepository = productDetailRepository;
        this.productQuery = productQuery;
        this.productRedisService = productRedisService;

    }

    @Override
    @Transactional(rollbackFor = {DataExistsException.class, DataNotFoundException.class})
    public Product save(ProductDto productDto) throws DataExistsException, DataNotFoundException {
        if (productRepository.existsByProductName(productDto.getProductName()))
            throw new DataExistsException("product is exists");
        Product product = productMapper.productDto2Product(productDto);
        product = super.save(product);
        if (productDto.getImages() != null && !productDto.getImages().isEmpty()) {
            List<MultipartFile> files = productDto.getImages();
            uploadImages(files, product, productDto.getThumbnail());
        }
        return super.save(product);
    }



    private ProductDetail mapToDto(ProductDetailDto pd) {
        return ProductDetail.builder()
                .color(new Color(pd.getColorId()))
                .size(new Size(pd.getSizeId()))
                .quantity(pd.getQuantity())
                .build();
    }

    private void uploadImages(List<MultipartFile> files, Product product, Integer thumbnailIndex) throws DataExistsException {
        for (MultipartFile file : files) {
            if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
                throw new DataExistsException("file is not image");
            }
            try {
                String path = s3Upload.uploadFile(file);
                ProductImage productImage = new ProductImage();
                productImage.setProduct(product);
                productImage.setPath(path);
                productImageRepository.save(productImage);
                if (thumbnailIndex != null &&
                        thumbnailIndex == files.indexOf(file)
                ) {
                    product.setThumbnail(path);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public ProductResponse findProductById(Long id) throws DataNotFoundException {
        Product product = super.findById(id)
                .orElseThrow(() -> new DataNotFoundException("product not found"));
        List<ProductDetail> productDetails = productDetailRepository.findByProductId(id);
        List<ProductImage> productImages = productImageRepository.findByProductId(id);
        return ProductResponse.builder()
                .product(product)
                .productDetails(productDetails)
                .productImages(productImages)
                .build();
    }

    @Override
    public PageResponse<?> getProductsForUserRole(int pageNo, int pageSize, String[] search, String[] sort)
            throws JsonProcessingException {
        PageResponse<?> result = productRedisService.getProductsInCache(pageNo, pageSize, search, sort);
        if (result == null) {
            result = productQuery.getPageData(pageNo, pageSize, search, sort);
            productRedisService.saveProductsInCache(result, pageNo, pageSize, search, sort);
        }
        return result;
    }

    @Override
    public PageResponse<?> getProductsSale(int pageNo, int pageSize, String[] search, String[] sort) throws JsonProcessingException {
        PageResponse<?> result = productRedisService.getProductsInCache(pageNo, pageSize, search, sort);
        if (result == null) {
            result = productQuery.getPageDataPromotion(pageNo, pageSize, search, sort);
            productRedisService.saveProductsInCache(result, pageNo, pageSize, search, sort);
        }
        return result;
    }

    @Override
    public Product update(Long id, Product product) throws DataNotFoundException {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("product not found"));
        oldProduct.setProductStatus(product.getProductStatus());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setProductName(product.getProductName());
        oldProduct.setProvider(product.getProvider());
        oldProduct.setCategory(product.getCategory());
        oldProduct.setThumbnail(product.getThumbnail());
        return productRepository.save(oldProduct);
    }
}
