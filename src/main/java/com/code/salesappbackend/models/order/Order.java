package com.code.salesappbackend.models.order;

import com.code.salesappbackend.models.BaseModel;
import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.models.address.Address;
import com.code.salesappbackend.models.enums.DeliveryMethod;
import com.code.salesappbackend.models.enums.OrderStatus;
import com.code.salesappbackend.models.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Order extends BaseModel {
    @Id
    @Column(name = "order_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
    @Column(columnDefinition = "text")
    private String note;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
    @Column(nullable = false, name = "phone_number", length = 10)
    private String phoneNumber;
    @Column(name = "buyer_name", nullable = false)
    private String buyerName;
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_method")
    private DeliveryMethod deliveryMethod;
    @Column(name = "original_amount", columnDefinition = "decimal(10,2)")
    private Double originalAmount;
    @Column(name = "discounted_price", columnDefinition = "decimal(10,2)")
    private Double discountedPrice;
    @Column(name = "discounted_amount", columnDefinition = "decimal(10,2)")
    private Double discountedAmount;
    @Column(name = "delivery_amount", columnDefinition = "decimal(10,2)")
    private Double deliveryAmount;
}
