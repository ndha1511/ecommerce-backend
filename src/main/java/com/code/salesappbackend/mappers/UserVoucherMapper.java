package com.code.salesappbackend.mappers;

import com.code.salesappbackend.dtos.requests.UserVoucherDto;
import com.code.salesappbackend.models.User;
import com.code.salesappbackend.models.UserVoucher;
import com.code.salesappbackend.models.Voucher;
import com.code.salesappbackend.services.interfaces.UserService;
import com.code.salesappbackend.services.interfaces.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserVoucherMapper {
    private final UserService userService;
    private final VoucherService voucherService;

    public UserVoucher userVoucherDto2UserVoucher(UserVoucherDto userVoucherDto) {
        UserVoucher userVoucher = new UserVoucher();
        userVoucher.setUsed(userVoucherDto.isUsed());
        User user = userService.findById(userVoucherDto.getUserId())
                .orElseThrow();
        Voucher voucher = voucherService.findById(userVoucherDto.getVoucherId())
                .orElseThrow();
        userVoucher.setVoucher(voucher);
        userVoucher.setUser(user);
        return userVoucher;
    }
}
