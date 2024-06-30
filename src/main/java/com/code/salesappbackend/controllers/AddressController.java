package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.AddressDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.AddressMapper;
import com.code.salesappbackend.models.Address;
import com.code.salesappbackend.services.interfaces.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;


    @PostMapping
    public Response addAddress(@RequestBody @Valid AddressDto addressDto) {
        Address address = addressMapper.addressDto2Address(addressDto);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "address created successfully",
                addressService.save(address));
    }

    @PutMapping("/{id}")
    public Response updateAddress(@PathVariable Long id, @RequestBody @Valid AddressDto addressDto)
            throws Exception {
        Address address = addressMapper.addressDto2Address(addressDto);
        address.setId(id);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "updated address",
                addressService.update(id, address));
    }

    @DeleteMapping("/{id}")
    public Response deleteAddress(@PathVariable Long id) {
        addressService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.NO_CONTENT.value(),
                "deleted address with id: " + id
        );
    }

    @PatchMapping("/{id}")
    public Response patchAddress(@PathVariable Long id, @RequestBody Map<String, ?> data)
            throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "updated address",
                addressService.updatePatch(id, data)
        );
    }
}
