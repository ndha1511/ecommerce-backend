package com.code.salesappbackend.dtos.requests.product;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProductPriceDto {
    @NotNull(message = "product id must be not null")
    private Long productId;
    @NotNull(message = "discount must be not null")
    @Range(min = 0, max = 1, message = "discount must be between to 0 and 1")
    private Float discount;
    @NotNull(message = "expired date not null")
    @Future(message = "expired date must be greater than current date")
    private LocalDateTime expiredDate;
    private String note;


}
