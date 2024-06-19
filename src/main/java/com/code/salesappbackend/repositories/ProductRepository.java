package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends BaseRepository<Product, Long> {
    boolean existsByProductName(String productName);
}