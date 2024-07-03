package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.LoginRequestDto;
import com.code.salesappbackend.dtos.requests.ResetPasswordRequest;
import com.code.salesappbackend.dtos.requests.UserRegisterDto;
import com.code.salesappbackend.dtos.requests.VerifyEmailDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.services.interfaces.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public Response register(@RequestBody @Valid UserRegisterDto userRegisterDto)
            throws Exception{
        authService.register(userRegisterDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "successfully registered",
                "check otp in your email"
        );
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginRequestDto loginRequestDto)
            throws Exception{
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "login successfully",
                authService.login(loginRequestDto)
        );
    }

    @PostMapping("/verify-email")
    public Response verifyEmail(@RequestBody @Valid VerifyEmailDto verifyEmailDto)
            throws Exception{
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "verify email successfully",
                authService.verifyEmail(verifyEmailDto)
        );
    }

    @PostMapping("/refresh-token")
    public Response refreshToken(@RequestBody String refreshToken)
            throws Exception{
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "refresh token successfully",
                authService.refreshToken(refreshToken)
        );
    }

    @PostMapping("/get-verify-code")
    public Response getVerifyCode(@RequestBody String email)
            throws Exception{
        authService.sendVerificationEmail(email);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "sent verification code successfully",
                null
        );
    }

    @PostMapping("/verify-reset-password-code")
    public Response verifyResetPasswordCode(@RequestBody VerifyEmailDto verifyEmailDto)
    throws Exception{
        authService.verificationEmailForResetPassword(verifyEmailDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "verify successfully",
                null
        );
    }

    @PostMapping("/reset-password")
    public Response resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest)
    throws Exception{
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "reset password successfully",
                authService.resetPassword(resetPasswordRequest)
        );
    }

}
