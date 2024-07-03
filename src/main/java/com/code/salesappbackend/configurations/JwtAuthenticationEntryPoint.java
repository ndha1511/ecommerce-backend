package com.code.salesappbackend.configurations;

import com.code.salesappbackend.dtos.responses.ResponseError;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        Throwable cause = authException.getCause();
        if (cause instanceof ExpiredJwtException) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), cause.getMessage());
        } else {
            response.sendError(HttpStatus.BAD_REQUEST.value(), authException.getMessage());
        }

    }
}
