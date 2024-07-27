package com.code.salesappbackend.services.interfaces.product;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.product.Category;
import com.code.salesappbackend.services.interfaces.BaseService;

public interface CategoryService extends BaseService<Category, Long> {
    void checkExistsCategoryName(String categoryName) throws DataExistsException;
}
