package com.code.salesappbackend.dtos.requests.product;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOrderDto {
    @NotNull(message = "product id must be not null")
    private Long productDetailId;
    @NotNull(message = "quantity must be not null")
    private Integer quantity;

}
