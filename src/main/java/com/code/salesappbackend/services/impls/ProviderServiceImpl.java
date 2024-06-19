package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.Provider;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.ProviderRepository;
import com.code.salesappbackend.services.interfaces.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl extends BaseServiceImpl<Provider, Long> implements ProviderService {
    private ProviderRepository providerRepository;

    public ProviderServiceImpl(BaseRepository<Provider, Long> repository) {
        super(repository);
    }

    @Autowired
    public void setProviderRepository(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }


    @Override
    public void checkExistsProviderName(String providerName) throws DataExistsException {
        if(providerRepository.existsByProviderName(providerName))
            throw new DataExistsException("provider exists");
    }
}
