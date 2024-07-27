package com.code.salesappbackend.dtos.requests.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserVoucherDto {
    @NotNull(message = "voucher id must be not null")
    private Long voucherId;
    @NotNull(message = "user id must be not null")
    private Long userId;
}
