package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.dtos.requests.LoginRequestDto;
import com.code.salesappbackend.dtos.requests.ResetPasswordRequest;
import com.code.salesappbackend.dtos.requests.UserRegisterDto;
import com.code.salesappbackend.dtos.requests.VerifyEmailDto;
import com.code.salesappbackend.dtos.responses.LoginResponse;
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
