package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}