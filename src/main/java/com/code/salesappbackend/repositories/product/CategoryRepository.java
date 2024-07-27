package com.code.salesappbackend.repositories.product;

import com.code.salesappbackend.models.product.Category;
import com.code.salesappbackend.repositories.BaseRepository;

public interface CategoryRepository extends BaseRepository<Category, Long> {
    boolean existsByCategoryName(String name);
}