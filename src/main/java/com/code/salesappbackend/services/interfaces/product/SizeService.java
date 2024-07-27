package com.code.salesappbackend.services.interfaces.product;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.product.Size;
import com.code.salesappbackend.services.interfaces.BaseService;

public interface SizeService extends BaseService<Size, Long> {
    void existsByTextSize(String textSize) throws DataExistsException;
    void existsByNumberSize(Short numberSize) throws DataExistsException;
}
