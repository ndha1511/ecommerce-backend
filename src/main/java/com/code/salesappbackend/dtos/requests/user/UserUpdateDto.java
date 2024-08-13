package com.code.salesappbackend.dtos.requests.user;

import com.code.salesappbackend.dtos.requests.address.AddressDto;
import com.code.salesappbackend.models.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserUpdateDto {
    @NotBlank(message = "name must be not blank")
    private String name;
    private String phoneNumber;
    private Gender gender;
    @Past(message = "current date must be greater than date of birth")
    private LocalDate dateOfBirth;
    private String avatarUrl;
    private AddressDto addressDto;
}
