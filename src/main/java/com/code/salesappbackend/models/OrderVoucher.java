package com.code.salesappbackend.models;

import com.code.salesappbackend.models.id_classes.OrderVoucherId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_vouchers")
@IdClass(OrderVoucherId.class)
public class OrderVoucher {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Id
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
}
