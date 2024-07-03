package com.code.salesappbackend.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
