package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.Category;

public interface CategoryService extends BaseService<Category, Long> {
    void checkExistsCategoryName(String categoryName) throws DataExistsException;
}
