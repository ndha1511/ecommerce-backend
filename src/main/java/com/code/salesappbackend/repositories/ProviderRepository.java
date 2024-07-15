package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Provider;

public interface ProviderRepository extends BaseRepository<Provider, Long> {
    boolean existsByProviderName(String providerName);
}