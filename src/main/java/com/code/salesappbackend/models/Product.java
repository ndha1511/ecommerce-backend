package com.code.salesappbackend.models;

import com.code.salesappbackend.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Builder
public class Product extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(nullable = false, unique = true, name = "product_name")
    private String productName;
    @Column(columnDefinition = "decimal(10,2)")
    private Double price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(columnDefinition = "decimal(10, 1)", name = "avg_rating")
    private Float avgRating;
    @Column(name = "number_of_rating")
    private Integer numberOfRating;
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;
    @Column(columnDefinition = "text")
    private String description;
    private String thumbnail;
    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
    private Status productStatus;
    @Column(name = "total_quantity")
    private Integer totalQuantity;
    @Column(name = "buy_quantity")
    private Integer buyQuantity;
}
