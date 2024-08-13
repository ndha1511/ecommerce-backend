package com.code.salesappbackend.repositories.product;

import com.code.salesappbackend.models.product.Product;
import com.code.salesappbackend.repositories.BaseRepository;


public interface ProductRepository extends BaseRepository<Product, Long> {

    boolean existsByProductName(String productName);

}