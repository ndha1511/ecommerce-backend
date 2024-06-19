package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.Address;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.services.interfaces.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address, Long> implements AddressService {
    public AddressServiceImpl(BaseRepository<Address, Long> repository) {
        super(repository);
    }
}
