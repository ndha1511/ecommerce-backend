package com.code.salesappbackend.controllers.voucher;

import com.code.salesappbackend.dtos.requests.voucher.VoucherDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.voucher.VoucherMapper;
import com.code.salesappbackend.models.voucher.Voucher;
import com.code.salesappbackend.services.interfaces.voucher.VoucherService;
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
    public Response addVoucher(@RequestBody VoucherDto voucherDto) {
        Voucher voucher = voucherMapper.voucherDto2Voucher(voucherDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create voucher successfully",
                voucherService.save(voucher)
        );
    }

    @GetMapping("/{email}")
    public Response getAllVouchers(@PathVariable String email)
            throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get vouchers successfully",
                voucherService.getVouchersByEmail(email)
        );
    }


}
