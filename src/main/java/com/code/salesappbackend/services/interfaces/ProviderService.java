package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.Provider;

public interface ProviderService extends BaseService<Provider, Long> {
    void checkExistsProviderName(String providerName) throws DataExistsException;
}
