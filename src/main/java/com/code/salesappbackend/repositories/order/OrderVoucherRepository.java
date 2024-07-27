package com.code.salesappbackend.repositories.order;

import com.code.salesappbackend.models.order.OrderVoucher;
import com.code.salesappbackend.models.id_classes.OrderVoucherId;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.List;

public interface OrderVoucherRepository extends BaseRepository<OrderVoucher, OrderVoucherId> {
    List<OrderVoucher> findAllByOrderId(String orderId);
}