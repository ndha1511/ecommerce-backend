package com.code.salesappbackend.services.impls.product;

import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.dtos.responses.product.ProductUserResponse;
import com.code.salesappbackend.services.interfaces.product.ProductRedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRedisServiceImpl implements ProductRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private String getRedisKey(int pageNo, int pageSize, String[] search, String[] sort) {
        StringBuilder key = new StringBuilder("products");
        key.append(":").append(pageNo);
        key.append(":").append(pageSize);
        if(search != null) {
            for (String s : search) {
                key.append(":").append(s);
            }
        }
        if(sort != null) {
            for (String s : sort) {
                key.append(":").append(s);
            }
        }
        return key.toString();
    }

    @Override
    public PageResponse<?> getProductsInCache(int pageNo, int pageSize, String[] search, String[] sort) throws JsonProcessingException {
        String key = getRedisKey(pageNo, pageSize, search, sort);
        String json = (String) redisTemplate.opsForValue().get(key);
        if (json != null) {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(PageResponse.class,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ProductUserResponse.class));
            return objectMapper.readValue(json, type);

        }
        return null;
    }

    @Override
    public void saveProductsInCache(PageResponse<?> products, int pageNo, int pageSize, String[] search, String[] sort) throws JsonProcessingException {
        String key = getRedisKey(pageNo, pageSize, search, sort);
        String json = objectMapper.writeValueAsString(products);
        redisTemplate.opsForValue().set(key, json);
    }

    @Override
    public void clearCache() {
        redisTemplate.execute((RedisCallback<Object>)  connection -> {
            connection.serverCommands().flushAll();
            return null;
        });
    }
}
