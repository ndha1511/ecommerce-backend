package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.OrderDetail;
import com.code.salesappbackend.models.id_classes.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}