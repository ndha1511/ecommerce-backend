package com.code.salesappbackend.models.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_security_hash")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSecurityHash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "hash_code")
    private String hashCode;
}
