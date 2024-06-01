package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.VoucherDto;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.VoucherMapper;
import com.code.salesappbackend.models.Voucher;
import com.code.salesappbackend.services.interfaces.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;
    private final VoucherMapper voucherMapper;

    @PostMapping
    public ResponseSuccess<?> addVoucher(@RequestBody VoucherDto voucherDto) {
        Voucher voucher = voucherMapper.voucherDto2Voucher(voucherDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create voucher successfully",
                voucherService.save(voucher)
        );
    }


}
