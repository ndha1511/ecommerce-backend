package com.code.salesappbackend.models.id_classes;

import com.code.salesappbackend.models.Order;
import com.code.salesappbackend.models.Voucher;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class OrderVoucherId {
    private Order order;
    private Voucher voucher;
}
