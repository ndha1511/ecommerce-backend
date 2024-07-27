package com.code.salesappbackend.repositories.product;

import com.code.salesappbackend.models.product.ProductPrice;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.List;

public interface ProductPriceRepository extends BaseRepository<ProductPrice, Long> {
    List<ProductPrice> findAllByProductId(Long productId);
}