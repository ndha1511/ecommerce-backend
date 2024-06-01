package com.code.salesappbackend.models.id_classes;

import com.code.salesappbackend.models.Order;
import com.code.salesappbackend.models.Voucher;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class OrderVoucherId implements Serializable {
    private Order order;
    private Voucher voucher;
}
