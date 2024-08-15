package com.code.salesappbackend.services.interfaces.order;

import com.code.salesappbackend.exceptions.DataNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface PaymentService {
    String payment(String orderId, HttpServletRequest req) throws UnsupportedEncodingException;
    String paymentSuccess(HttpServletRequest req) throws DataNotFoundException;
}
