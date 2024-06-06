package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.ProductPrice;
import com.code.salesappbackend.services.interfaces.ProductPriceService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceServiceImpl extends BaseServiceImpl<ProductPrice, Long> implements ProductPriceService {
    public ProductPriceServiceImpl(JpaRepository<ProductPrice, Long> repository) {
        super(repository);
    }
}
