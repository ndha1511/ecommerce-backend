package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Category;

public interface CategoryRepository extends BaseRepository<Category, Long> {
    boolean existsByCategoryName(String name);
}