package com.code.salesappbackend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserVoucherDto {
    private Long voucherId;
    private Long userId;
    private boolean isUsed;
}
