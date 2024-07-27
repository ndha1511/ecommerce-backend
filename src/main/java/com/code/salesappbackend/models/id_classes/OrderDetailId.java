package com.code.salesappbackend.models.id_classes;

import com.code.salesappbackend.models.order.Order;
import com.code.salesappbackend.models.product.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class OrderDetailId implements Serializable {
    private Order order;
    private ProductDetail productDetail;

}
