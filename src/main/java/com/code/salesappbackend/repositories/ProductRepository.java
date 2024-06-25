package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Product;

public interface ProductRepository extends BaseRepository<Product, Long> {
    boolean existsByProductName(String productName);
}