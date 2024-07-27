package com.code.salesappbackend.services.interfaces.auth;

import com.code.salesappbackend.dtos.requests.auth.LoginRequestDto;
import com.code.salesappbackend.dtos.requests.auth.ResetPasswordRequest;
import com.code.salesappbackend.dtos.requests.auth.UserRegisterDto;
import com.code.salesappbackend.dtos.requests.auth.VerifyEmailDto;
import com.code.salesappbackend.dtos.responses.auth.LoginResponse;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import jakarta.mail.MessagingException;

public interface AuthService {
    void register(UserRegisterDto userRegisterDto) throws DataExistsException, MessagingException;
    LoginResponse login(LoginRequestDto loginRequestDto) throws DataNotFoundException;
    LoginResponse verifyEmail(VerifyEmailDto verifyEmailDto) throws DataNotFoundException;
    LoginResponse refreshToken(String refreshToken) throws DataNotFoundException;
    void sendVerificationEmail(String email) throws MessagingException, DataNotFoundException;
    LoginResponse resetPassword(ResetPasswordRequest resetPasswordRequest) throws DataNotFoundException;
    void verificationEmailForResetPassword(VerifyEmailDto verifyEmailDto) throws DataNotFoundException;
    void resendVerificationEmail(String email) throws MessagingException, DataNotFoundException;
    void logout(String accessToken) throws DataNotFoundException;
}
