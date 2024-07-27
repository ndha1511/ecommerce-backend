package com.code.salesappbackend.services.impls.product;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.product.Color;
import com.code.salesappbackend.models.product.Product;
import com.code.salesappbackend.models.product.ProductDetail;
import com.code.salesappbackend.models.product.Size;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.product.ProductDetailRepository;
import com.code.salesappbackend.repositories.product.ProductRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.product.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductDetailServiceImpl extends BaseServiceImpl<ProductDetail, Long> implements ProductDetailService {
    private ProductDetailRepository productDetailRepository;
    private ProductRepository productRepository;
    public ProductDetailServiceImpl(BaseRepository<ProductDetail, Long> repository) {
        super(repository, ProductDetail.class);
    }

    @Autowired
    public void setProductDetailRepository(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductDetail save(ProductDetail productDetail) {
        Product product = productDetail.getProduct();
        int quantity = product.getTotalQuantity() != null ? product.getTotalQuantity() : 0;
        product.setTotalQuantity(quantity + productDetail.getQuantity());
        productRepository.save(product);
        return super.save(productDetail);
    }

    @Override
    public void existByAll(Color color, Size size, Product product) throws DataExistsException {
        if(productDetailRepository.existsByColorAndProductAndSize(color, product, size)) {
            throw new DataExistsException("product detail is exists");
        }
    }
}
