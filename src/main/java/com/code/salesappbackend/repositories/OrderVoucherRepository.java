package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.OrderVoucher;
import com.code.salesappbackend.models.id_classes.OrderVoucherId;

import java.util.List;

public interface OrderVoucherRepository extends BaseRepository<OrderVoucher, OrderVoucherId> {
    List<OrderVoucher> findAllByOrderId(String orderId);
}