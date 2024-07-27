package com.code.salesappbackend.services.impls.address;

import com.code.salesappbackend.models.address.Address;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.address.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address, Long> implements AddressService {
    public AddressServiceImpl(BaseRepository<Address, Long> repository) {
        super(repository, Address.class);
    }
}
