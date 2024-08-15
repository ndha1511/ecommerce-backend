package com.code.salesappbackend.dtos.requests.order;

import com.code.salesappbackend.models.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateDto {
    private OrderStatus status;
}
