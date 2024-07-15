package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.ProductImage;

import java.util.List;

public interface ProductImageRepository extends BaseRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}