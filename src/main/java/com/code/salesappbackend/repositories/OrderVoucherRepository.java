package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.OrderVoucher;
import com.code.salesappbackend.models.id_classes.OrderVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderVoucherRepository extends JpaRepository<OrderVoucher, OrderVoucherId> {
    List<OrderVoucher> findAllByOrderId(String orderId);
}