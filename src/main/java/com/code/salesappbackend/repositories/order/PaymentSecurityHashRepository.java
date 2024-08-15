package com.code.salesappbackend.repositories.order;

import com.code.salesappbackend.models.order.PaymentSecurityHash;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.Optional;

public interface PaymentSecurityHashRepository extends BaseRepository<PaymentSecurityHash, Long> {
    Optional<PaymentSecurityHash> findByOrderIdAndHashCode(String orderId, String hash);
    Optional<PaymentSecurityHash> findByOrderId(String orderId);
}
