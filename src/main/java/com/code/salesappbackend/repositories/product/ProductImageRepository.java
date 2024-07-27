package com.code.salesappbackend.repositories.product;

import com.code.salesappbackend.models.product.ProductImage;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends BaseRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
    Optional<ProductImage> findByPath(String path);
}