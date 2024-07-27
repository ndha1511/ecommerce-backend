package com.code.salesappbackend.models.product;

import com.code.salesappbackend.events.ProductPriceEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import static com.code.salesappbackend.utils.RoundData.round;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_prices")
@Builder
@EntityListeners(ProductPriceEvent.class)
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_price_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;
    private Float discount;
    @Column(name = "discounted_price", columnDefinition = "decimal(10,2)")
    private Double discountedPrice;
    @Column(name = "discounted_amount", columnDefinition = "decimal(10,2)")
    private Double discountedAmount;
    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredDate;
    @Column(columnDefinition = "text")
    private String note;


    public void setDiscountedAmount(Double value) {
        this.discountedAmount = round(value);
    }

    public void setDiscountedPrice(Double value) {
        this.discountedPrice = round(value);
    }

    public Double getDiscountedAmount() {
        return round(this.discountedAmount);
    }

    public Double getDiscountedPrice() {
        return round(this.discountedPrice);
    }
}
