package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.dtos.responses.PageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProductRedisService {
    PageResponse<?> getProductsInCache(int pageNo, int pageSize, String[] search, String[] sort) throws JsonProcessingException;
    void saveProductsInCache(PageResponse<?> products, int pageNo, int pageSize, String[] search, String[] sort) throws JsonProcessingException;
    void clearCache();
}
