package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}