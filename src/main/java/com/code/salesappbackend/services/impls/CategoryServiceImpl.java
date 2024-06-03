package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.Category;
import com.code.salesappbackend.repositories.CategoryRepository;
import com.code.salesappbackend.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService {
    private CategoryRepository repository;

    public CategoryServiceImpl(JpaRepository<Category, Long> repository) {
        super(repository);
    }

    @Autowired
    public void setRepository(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void checkExistsCategoryName(String categoryName) throws DataExistsException {
        if(repository.existsByCategoryName(categoryName))
            throw new DataExistsException("category exists");
    }
}
