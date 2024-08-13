package com.code.salesappbackend.dtos.requests.order;

import com.code.salesappbackend.models.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateDto {
    @NotEmpty(message = "status not null")
    private OrderStatus status;
}
