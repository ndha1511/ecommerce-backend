package com.code.salesappbackend.repositories.product;

import com.code.salesappbackend.models.product.Provider;
import com.code.salesappbackend.repositories.BaseRepository;

public interface ProviderRepository extends BaseRepository<Provider, Long> {
    boolean existsByProviderName(String providerName);
}