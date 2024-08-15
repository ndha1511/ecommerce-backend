package com.code.salesappbackend.repositories.order;

import com.code.salesappbackend.models.order.OrderDetail;
import com.code.salesappbackend.models.id_classes.OrderDetailId;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.List;

public interface OrderDetailRepository extends BaseRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByOrderId(String orderId);
}