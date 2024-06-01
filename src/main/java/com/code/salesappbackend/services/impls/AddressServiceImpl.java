package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.Address;
import com.code.salesappbackend.services.interfaces.AddressService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address, Long> implements AddressService {

    public AddressServiceImpl(JpaRepository<Address, Long> repository) {
        super(repository);
    }
}
