package com.code.salesappbackend.mappers;

import com.code.salesappbackend.dtos.requests.VoucherDto;
import com.code.salesappbackend.models.Voucher;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {

    public Voucher voucherDto2Voucher(VoucherDto voucherDto) {
        return Voucher.builder()
                .voucherType(voucherDto.getVoucherType())
                .discount(voucherDto.getDiscount())
                .expiredDate(voucherDto.getExpiredDate())
                .minAmount(voucherDto.getMinAmount())
                .maxPrice(voucherDto.getMaxPrice())
                .quantity(voucherDto.getQuantity())
                .build();
    }
}
