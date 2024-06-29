package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.dtos.requests.ProductDto;
import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.dtos.responses.products.ProductResponse;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.mappers.ProductMapper;
import com.code.salesappbackend.models.Product;
import com.code.salesappbackend.models.ProductDetail;
import com.code.salesappbackend.models.ProductImage;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.ProductDetailRepository;
import com.code.salesappbackend.repositories.ProductImageRepository;
import com.code.salesappbackend.repositories.ProductRepository;
import com.code.salesappbackend.repositories.customizations.ProductQuery;
import com.code.salesappbackend.services.interfaces.ProductService;
import com.code.salesappbackend.utils.CloudinaryUpload;
import com.code.salesappbackend.utils.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {
    private ProductMapper productMapper;
    private ProductRepository productRepository;
    private ProductImageRepository productImageRepository;
    private CloudinaryUpload cloudinaryUpload;
    private S3Upload s3Upload;
    private ProductDetailRepository productDetailRepository;
    private ProductQuery productQuery;

    @Autowired
    public void setProductQuery(ProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    public ProductServiceImpl(BaseRepository<Product, Long> repository) {
        super(repository, Product.class);
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductImageRepository(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Autowired
    public void setProductDetailRepository(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    @Autowired
    public void setCloudinaryUpload(CloudinaryUpload cloudinaryUpload) {
        this.cloudinaryUpload = cloudinaryUpload;
    }
    @Autowired
    public void setS3Upload(S3Upload s3Upload) {
        this.s3Upload = s3Upload;
    }

    @Override
    @Transactional(rollbackFor = {DataExistsException.class, DataNotFoundException.class})
    public Product save(ProductDto productDto) throws DataExistsException, DataNotFoundException {
        if(productRepository.existsByProductName(productDto.getProductName()))
            throw new DataExistsException("product is exists");
        Product product = productMapper.productDto2Product(productDto);
        product = super.save(product);
        if(!productDto.getImages().isEmpty()) {
            List<MultipartFile> files = productDto.getImages();
            for (MultipartFile file : files) {
                if(!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
                    throw new DataExistsException("file is not image");
                }
                try {
                    String path = s3Upload.uploadFile(file);
                    ProductImage productImage = new ProductImage();
                    productImage.setProduct(product);
                    productImage.setPath(path);
                    productImageRepository.save(productImage);
                    if(productDto.getThumbnail() != null &&
                            productDto.getThumbnail() == files.indexOf(file)
                    ) {
                        product.setThumbnail(path);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return super.save(product);
    }

    @Override
    public ProductResponse findProductById(Long id) throws DataNotFoundException{
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
    public PageResponse<?> getProductsForUserRole(int pageNo, int pageSize, String[] search, String[] sort) throws NoSuchFieldException {
        return productQuery.getPageData(pageNo, pageSize, search, sort);
    }

}
