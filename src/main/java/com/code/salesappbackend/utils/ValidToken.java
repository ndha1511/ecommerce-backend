package com.code.salesappbackend.utils;

import com.code.salesappbackend.exceptions.UserNotVerifyException;
import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.repositories.user.UserRepository;
import com.code.salesappbackend.services.interfaces.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ValidToken {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void valid(String email, HttpServletRequest request) throws UserNotVerifyException {
        String token = request.getHeader("Authorization").substring(7);
        if(!email.equals(jwtService.extractEmail(token))) {
            throw new UserNotVerifyException("Invalid token");
        };
    }

    public void valid(Long id, HttpServletRequest request) throws UserNotVerifyException {
        String token = request.getHeader("Authorization").substring(7);
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotVerifyException("Invalid token"));
        if(!user.getEmail().equals(jwtService.extractEmail(token))) {
            throw new UserNotVerifyException("Invalid token");
        }
    }
}
