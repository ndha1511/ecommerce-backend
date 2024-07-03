package com.code.salesappbackend.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResetPasswordRequest {
    String email;
    String password;
    String otpResetPassword;
}
