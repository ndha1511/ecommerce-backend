package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.ProductPrice;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.services.interfaces.ProductPriceService;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceServiceImpl extends BaseServiceImpl<ProductPrice, Long> implements ProductPriceService {
    public ProductPriceServiceImpl(BaseRepository<ProductPrice, Long> repository) {
        super(repository);
    }
}
