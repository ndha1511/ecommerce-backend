package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Color;
import com.code.salesappbackend.models.Product;
import com.code.salesappbackend.models.ProductDetail;
import com.code.salesappbackend.models.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    boolean existsByColorAndProductAndSize(Color color, Product product, Size size);
}