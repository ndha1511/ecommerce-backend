package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Address;

import java.util.Optional;

public interface AddressRepository extends BaseRepository<Address, Long> {
    Optional<Address> findByCityAndDistrictAndStreet(String city, String district, String street);
}