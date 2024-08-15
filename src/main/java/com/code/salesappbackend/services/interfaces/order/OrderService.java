package com.code.salesappbackend.services.interfaces.order;

import com.code.salesappbackend.dtos.requests.order.OrderDto;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.exceptions.OutOfInStockException;
import com.code.salesappbackend.models.enums.OrderStatus;
import com.code.salesappbackend.models.order.Order;
import com.code.salesappbackend.models.order.OrderDetail;
import com.code.salesappbackend.services.interfaces.BaseService;

import java.util.List;

public interface OrderService extends BaseService<Order, String> {
    Order save(OrderDto orderDto) throws DataNotFoundException, OutOfInStockException;
    Order updateStatusOrder(String id, OrderStatus status) throws DataNotFoundException;
    List<OrderDetail> getOrderDetailsByOrderId(String orderId);

}
