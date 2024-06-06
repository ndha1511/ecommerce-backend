package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.Color;

public interface ColorService extends BaseService<Color, Long>{
    void existsByColorName(String colorName) throws DataExistsException;
}
