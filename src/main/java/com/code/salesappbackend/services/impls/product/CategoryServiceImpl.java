package com.code.salesappbackend.services.impls.product;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.product.Category;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.product.CategoryRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.product.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService {
    private CategoryRepository repository;

    public CategoryServiceImpl(BaseRepository<Category, Long> repository) {
        super(repository, Category.class);
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
