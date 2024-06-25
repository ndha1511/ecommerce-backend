package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.dtos.requests.ProductDto;
import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.dtos.responses.ProductResponse;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService extends BaseService<Product, Long> {
    Product save(ProductDto productDto) throws DataExistsException, DataNotFoundException;
    ProductResponse findProductById(Long id) throws DataNotFoundException;

}
