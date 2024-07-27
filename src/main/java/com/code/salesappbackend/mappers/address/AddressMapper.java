package com.code.salesappbackend.mappers.address;

import com.code.salesappbackend.dtos.requests.address.AddressDto;
import com.code.salesappbackend.models.address.Address;
import com.code.salesappbackend.repositories.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AddressMapper {
    private final AddressRepository addressRepository;

    public Address addressDto2Address(AddressDto addressDto) {
        return addressRepository
                .findByCityAndDistrictAndStreet(
                        addressDto.getCity(),
                        addressDto.getDistrict(),
                        addressDto.getStreet())
                .orElse(Address.builder()
                        .street(addressDto.getStreet())
                        .city(addressDto.getCity())
                        .district(addressDto.getDistrict())
                        .build());
    }
}
