package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.dtos.requests.ProductDto;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.Product;

public interface ProductService extends BaseService<Product, Long> {
    Product save(ProductDto productDto) throws DataExistsException, DataNotFoundException;
}
