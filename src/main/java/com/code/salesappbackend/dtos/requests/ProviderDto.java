package com.code.salesappbackend.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProviderDto {
    @NotBlank(message = "provider name must be not blank")
    private String providerName;
    private AddressDto address;
    @Pattern(message = "phone number is invalid", regexp = "^0\\d{9}")
    @NotBlank(message = "phone number must be not blank")
    private String phoneNumber;
    @Pattern(message = "email is invalid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    @NotBlank(message = "email must be not blank")
    private String email;

}
