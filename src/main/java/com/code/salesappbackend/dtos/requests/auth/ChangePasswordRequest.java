package com.code.salesappbackend.dtos.requests.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {
    @NotBlank(message = "email must be not blank")
    private String email;
    @NotBlank(message = "old password must be not blank")
    private String oldPassword;
    @NotBlank(message = "new password must be not blank")
    private String newPassword;
}
