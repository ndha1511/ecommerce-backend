package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.ProductPrice;

import java.util.List;

public interface ProductPriceRepository extends BaseRepository<ProductPrice, Long> {
    List<ProductPrice> findAllByProductId(Long productId);
}