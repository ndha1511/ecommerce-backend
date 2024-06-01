package com.code.salesappbackend.mappers;

import com.code.salesappbackend.dtos.requests.AddressDto;
import com.code.salesappbackend.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public Address addressDto2Address(AddressDto addressDto) {
        return Address.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .district(addressDto.getDistrict())
                .build();
    }
}
