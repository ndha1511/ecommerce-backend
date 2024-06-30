package com.code.salesappbackend.events;

import com.code.salesappbackend.models.Product;
import com.code.salesappbackend.services.interfaces.ProductRedisService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ProductEvent {
    private final ProductRedisService productRedisService;

    @PostPersist
    public void postPersist(Product product) {
        productRedisService.clearCache();
    }

    @PostUpdate
    public void postUpdate(Product product) {
        productRedisService.clearCache();
    }

    @PostRemove
    public void postRemove(Product product) {
        productRedisService.clearCache();
    }


}
