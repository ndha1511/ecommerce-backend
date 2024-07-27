package com.code.salesappbackend.services.interfaces.product;

import com.code.salesappbackend.dtos.requests.product.ProductDto;
import com.code.salesappbackend.dtos.requests.product.ProductUpdateDto;
import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.dtos.responses.product.ProductResponse;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.product.Product;
import com.code.salesappbackend.services.interfaces.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProductService extends BaseService<Product, Long> {
    Product save(ProductDto productDto) throws DataExistsException, DataNotFoundException;
    Product updateProduct(Long productId, ProductUpdateDto productUpdateDto) throws DataExistsException;
    ProductResponse findProductById(Long id) throws DataNotFoundException;
    PageResponse<?> getProductsForUserRole(int pageNo, int pageSize, String[] search, String[] sort) throws JsonProcessingException;
}
