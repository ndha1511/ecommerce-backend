package com.code.salesappbackend.models;

import com.code.salesappbackend.models.enums.Scope;
import com.code.salesappbackend.models.enums.VoucherType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Long id;
    @Column(name = "max_price", columnDefinition = "decimal(10,2)")
    private Double maxPrice;
    @Column(name = "min_amount", columnDefinition = "decimal(10,2)")
    private Double minAmount;
    private Float discount;
    @Enumerated(EnumType.STRING)
    @Column(name = "voucher_type")
    private VoucherType voucherType;
    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiredDate;
    @Enumerated(EnumType.STRING)
    private Scope scope;
    private Integer quantity;
}
