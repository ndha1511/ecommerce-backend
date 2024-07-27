package com.code.salesappbackend.services.interfaces.order;

import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface PaymentService {
    String payment(HttpServletRequest req) throws UnsupportedEncodingException;
}
