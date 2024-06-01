package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.UserVoucherDto;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.UserVoucherMapper;
import com.code.salesappbackend.models.UserVoucher;
import com.code.salesappbackend.models.id_classes.UserVoucherId;
import com.code.salesappbackend.services.interfaces.UserVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userVoucher")
@RequiredArgsConstructor
public class UserVoucherController {
    private final UserVoucherService userVoucherService;
    private final UserVoucherMapper userVoucherMapper;

    @PostMapping
    public ResponseSuccess<?> addUserVoucher(@RequestBody UserVoucherDto userVoucherDto) {
        UserVoucher userVoucher = userVoucherMapper.userVoucherDto2UserVoucher(userVoucherDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "add user voucher successfully",
                userVoucherService.save(userVoucher)
        );
    }

    @PutMapping
    public ResponseSuccess<?> updateUserVoucher(@RequestBody UserVoucherDto userVoucherDto) {
        UserVoucher userVoucher = userVoucherMapper.userVoucherDto2UserVoucher(userVoucherDto);
        UserVoucherId userVoucherId = new UserVoucherId(userVoucher.getUser(), userVoucher.getVoucher());
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update user voucher successfully",
                userVoucherService.update(userVoucherId, userVoucher)
        );
    }
}
