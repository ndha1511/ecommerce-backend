package com.code.salesappbackend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;

public class GenerateSecretCode {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256 bits (32 bytes) is a common choice for HMAC-SHA256
        random.nextBytes(keyBytes);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("w3tu5mFXgFlYEB66"));
        String secretKey = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Generated SECRET_KEY : " + secretKey);
    }
}
