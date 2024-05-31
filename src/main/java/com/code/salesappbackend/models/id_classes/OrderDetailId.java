package com.code.salesappbackend.models.id_classes;

import com.code.salesappbackend.models.Order;
import com.code.salesappbackend.models.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
public class OrderDetailId implements Serializable {
    private Order order;
    private ProductDetail productDetail;

}
