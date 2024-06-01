package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.Category;
import com.code.salesappbackend.services.interfaces.CategoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService {
    public CategoryServiceImpl(JpaRepository<Category, Long> repository) {
        super(repository);
    }
}
