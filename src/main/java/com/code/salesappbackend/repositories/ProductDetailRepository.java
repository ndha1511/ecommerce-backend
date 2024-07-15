package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Color;
import com.code.salesappbackend.models.Product;
import com.code.salesappbackend.models.ProductDetail;
import com.code.salesappbackend.models.Size;

import java.util.List;

public interface ProductDetailRepository extends BaseRepository<ProductDetail, Long> {
    boolean existsByColorAndProductAndSize(Color color, Product product, Size size);
    List<ProductDetail> findByProductId(Long productId);
}