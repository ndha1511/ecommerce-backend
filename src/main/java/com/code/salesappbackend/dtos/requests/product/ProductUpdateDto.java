package com.code.salesappbackend.dtos.requests.product;

import com.code.salesappbackend.models.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductUpdateDto {
    @NotBlank(message = "product name must be not blank")
    private String productName;
    @NotNull(message = "price must be not null")
    private Double price;
    private String description;
    private String thumbnail;
    @NotNull(message = "category must be not null")
    private Long categoryId;
    @NotNull(message = "provider must be not null")
    private Long providerId;
    private Status status;
}
