package com.code.salesappbackend.services.interfaces.product;

import com.code.salesappbackend.models.product.ProductPrice;
import com.code.salesappbackend.services.interfaces.BaseService;

import java.util.List;

public interface ProductPriceService extends BaseService<ProductPrice, Long> {
    List<ProductPrice> findByProductId(Long productId);
}
