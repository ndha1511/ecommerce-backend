package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Product;
import com.code.salesappbackend.repositories.criteria.ProductRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends BaseRepository<Product, Long>, ProductRepositoryCustom {
    boolean existsByProductName(String productName);
}