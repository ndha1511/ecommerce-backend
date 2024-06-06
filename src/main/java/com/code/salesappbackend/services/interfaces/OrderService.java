package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.dtos.requests.OrderDto;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.Order;

public interface OrderService extends BaseService<Order, String> {
    Order save(OrderDto orderDto) throws DataNotFoundException;

}
