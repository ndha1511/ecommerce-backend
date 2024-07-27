package com.code.salesappbackend.services.interfaces.product;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.product.Color;
import com.code.salesappbackend.services.interfaces.BaseService;

public interface ColorService extends BaseService<Color, Long> {
    void existsByColorName(String colorName) throws DataExistsException;
}
