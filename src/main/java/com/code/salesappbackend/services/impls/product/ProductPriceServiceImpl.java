package com.code.salesappbackend.services.impls.product;

import com.code.salesappbackend.models.product.ProductPrice;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.product.ProductPriceRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.product.ProductPriceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPriceServiceImpl extends BaseServiceImpl<ProductPrice, Long> implements ProductPriceService {
    private final ProductPriceRepository productPriceRepository;

    public ProductPriceServiceImpl(BaseRepository<ProductPrice, Long> repository, ProductPriceRepository productPriceRepository) {
        super(repository, ProductPrice.class);
        this.productPriceRepository = productPriceRepository;
    }

    @Override
    public List<ProductPrice> findByProductId(Long productId) {
        return productPriceRepository.findAllByProductId(productId);
    }
}
