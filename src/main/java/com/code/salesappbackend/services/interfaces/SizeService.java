package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.Size;

public interface SizeService extends BaseService<Size, Long>{
    void existsByTextSize(String textSize) throws DataExistsException;
    void existsByNumberSize(Short numberSize) throws DataExistsException;
}
