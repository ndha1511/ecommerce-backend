package com.code.salesappbackend.models;

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
@Table(name = "product_prices")
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_price_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private Float discount;
    @Column(name = "discounted_price", columnDefinition = "decimal(10,2)")
    private Double discountedPrice;
    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiredDate;
    @Column(columnDefinition = "text")
    private String note;
}
