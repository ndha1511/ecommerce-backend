package com.code.salesappbackend.dtos.requests;

import com.code.salesappbackend.models.enums.Gender;
import com.code.salesappbackend.models.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.code.salesappbackend.models.User}
 */

@Getter
@Setter
@Builder
public class UserDto implements Serializable {
    @NotBlank(message = "name must be not blank")
    private String name;
    @Pattern(message = "email is invalid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    @NotBlank(message = "email must be not blank")
    private String email;
    @NotBlank
    private String password;
    @Pattern(message = "phone number is invalid", regexp = "^0\\d{9}")
    @NotBlank(message = "phone number must be not blank")
    private String phoneNumber;
    private Gender gender;
    @Past(message = "current date must be greater than date of birth")
    private LocalDate dateOfBirth;
    private Role role;
    private AddressDto address;
}