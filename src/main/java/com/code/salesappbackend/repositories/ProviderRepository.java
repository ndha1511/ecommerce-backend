package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends BaseRepository<Provider, Long> {
    boolean existsByProviderName(String providerName);
}