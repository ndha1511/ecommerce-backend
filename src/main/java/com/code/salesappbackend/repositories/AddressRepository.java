package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}