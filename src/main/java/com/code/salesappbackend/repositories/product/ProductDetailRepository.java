package com.code.salesappbackend.repositories.product;

import com.code.salesappbackend.models.product.Color;
import com.code.salesappbackend.models.product.Product;
import com.code.salesappbackend.models.product.ProductDetail;
import com.code.salesappbackend.models.product.Size;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepository extends BaseRepository<ProductDetail, Long> {
    boolean existsByColorAndProductAndSize(Color color, Product product, Size size);
    Optional<ProductDetail> findByColorIdAndSizeIdAndProductId(Long colorId, Long sizeId, Long productId);
    List<ProductDetail> findByProductId(Long productId);
}