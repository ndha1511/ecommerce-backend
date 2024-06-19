package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends BaseRepository<Category, Long> {
    boolean existsByCategoryName(String name);
}