package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.Order;
import com.code.salesappbackend.services.interfaces.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {
    public OrderServiceImpl(JpaRepository<Order, String> repository) {
        super(repository);
    }
}
