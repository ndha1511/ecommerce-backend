package com.code.salesappbackend.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDetailDto {
    @NotNull(message = "product id must be not null")
    private Long productId;
    @NotNull(message = "size id must be not null")
    private Long sizeId;
    @NotNull(message = "color id must be not null")
    private Long colorId;
    @NotNull(message = "quantity")
    private Integer quantity;

}
