package com.code.salesappbackend.controllers.order;

import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.services.interfaces.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public Response getOrderDetailsByOrderId(@PathVariable final String orderId) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                orderService.getOrderDetailsByOrderId(orderId)
        );
    }
}
