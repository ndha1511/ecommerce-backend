package com.code.salesappbackend.dtos.requests;

import com.code.salesappbackend.models.Voucher;
import com.code.salesappbackend.models.enums.VoucherType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Voucher}
 */
@Setter
@Getter
public class VoucherDto implements Serializable {
    @NotNull(message = "max price must be not null")
    private Double maxPrice;
    @NotNull(message = "min amount must be not null")
    private Double minAmount;
    @NotNull(message = "discount must be not null")
    private Float discount;
    private VoucherType voucherType;
    @NotNull(message = "expired date must be not null")
    private LocalDateTime expiredDate;
    private Integer quantity;
}