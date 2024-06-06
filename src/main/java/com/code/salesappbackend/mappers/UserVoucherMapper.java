package com.code.salesappbackend.mappers;

import com.code.salesappbackend.dtos.requests.UserVoucherDto;
import com.code.salesappbackend.exceptions.DataNotFoundException;
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

    public UserVoucher userVoucherDto2UserVoucher(UserVoucherDto userVoucherDto) throws DataNotFoundException {
        User user = userService.findById(userVoucherDto.getUserId())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        Voucher voucher = voucherService.findById(userVoucherDto.getVoucherId())
                .orElseThrow(() -> new DataNotFoundException("voucher not found"));
        UserVoucher userVoucher = new UserVoucher();
        userVoucher.setUsed(false);
        userVoucher.setVoucher(voucher);
        userVoucher.setUser(user);
        return userVoucher;
    }
}
