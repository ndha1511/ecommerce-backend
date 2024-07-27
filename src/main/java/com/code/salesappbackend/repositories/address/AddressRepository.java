package com.code.salesappbackend.repositories.address;

import com.code.salesappbackend.models.address.Address;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.Optional;

public interface AddressRepository extends BaseRepository<Address, Long> {
    Optional<Address> findByCityAndDistrictAndStreet(String city, String district, String street);
}