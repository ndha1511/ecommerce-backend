package com.code.salesappbackend.mappers;

import com.code.salesappbackend.dtos.requests.CategoryDto;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.Category;
import com.code.salesappbackend.models.enums.Status;
import com.code.salesappbackend.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final CategoryService categoryService;

    public Category categoryDto2Category(CategoryDto categoryDto) throws DataExistsException {
        categoryService.checkExistsCategoryName(categoryDto.getCategoryName());
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setStatus(Status.ACTIVE);
        return category;
    }
}
