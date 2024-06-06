package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.Color;
import com.code.salesappbackend.models.Product;
import com.code.salesappbackend.models.ProductDetail;
import com.code.salesappbackend.models.Size;
import com.code.salesappbackend.repositories.ProductDetailRepository;
import com.code.salesappbackend.repositories.ProductRepository;
import com.code.salesappbackend.services.interfaces.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductDetailServiceImpl extends BaseServiceImpl<ProductDetail, Long> implements ProductDetailService {
    private ProductDetailRepository productDetailRepository;
    private ProductRepository productRepository;
    public ProductDetailServiceImpl(JpaRepository<ProductDetail, Long> repository) {
        super(repository);
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
