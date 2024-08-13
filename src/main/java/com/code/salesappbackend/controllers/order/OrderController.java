package com.code.salesappbackend.controllers.order;

import com.code.salesappbackend.dtos.requests.order.OrderDto;
import com.code.salesappbackend.dtos.requests.order.OrderUpdateDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.services.interfaces.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/{id}")
    public Response getOrderById(@PathVariable String id) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(), "success",
                orderService.findById(id)
        );
    }

    @GetMapping
    public Response getAllOrders(@RequestParam(defaultValue = "1") int pageNo,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(required = false) String[] sort,
                                 @RequestParam(required = false) String[] search) throws Exception{
        return new ResponseSuccess<>(
                HttpStatus.OK.value(), "success",
                orderService.getPageData(pageNo, pageSize, search, sort)
        );
    }

    @PutMapping("/{id}")
    public Response updateStatusOrder(@PathVariable String id,
                                      @RequestBody @Valid OrderUpdateDto orderDto)
    throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(), "success",
                orderService.updateStatusOrder(id, orderDto.getStatus())
        );
    }


    @PatchMapping("/{id}")
    public Response updateOrder(@PathVariable String id, @RequestBody @Valid Map<String, ?> data)
    throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(), "success",
                orderService.updatePatch(id, data)
        );
    }
}
