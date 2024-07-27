package com.code.salesappbackend.services.interfaces.product;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.product.Provider;
import com.code.salesappbackend.services.interfaces.BaseService;

public interface ProviderService extends BaseService<Provider, Long> {
    void checkExistsProviderName(String providerName) throws DataExistsException;
}
