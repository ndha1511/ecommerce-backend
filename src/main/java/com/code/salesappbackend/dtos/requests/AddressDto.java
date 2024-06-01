package com.code.salesappbackend.dtos.requests;

import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AddressDto {
    @NotBlank(message = "street must be not blank")
    private String street;
    @NotBlank(message = "district must be not blank")
    private String district;
    @NotBlank(message = "city must be not blank")
    private String city;
}