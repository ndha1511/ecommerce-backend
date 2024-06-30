package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.OrderDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.services.interfaces.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Response createOrder(@RequestBody @Valid OrderDto orderDto)
    throws Exception{
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create order successfully",
                orderService.save(orderDto)
        );
    }
}
