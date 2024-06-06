package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    List<ProductPrice> findAllByProductId(Long productId);
}