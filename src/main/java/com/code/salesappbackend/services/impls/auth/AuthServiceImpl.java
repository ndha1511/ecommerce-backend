package com.code.salesappbackend.services.impls.auth;

import com.code.salesappbackend.dtos.requests.auth.LoginRequestDto;
import com.code.salesappbackend.dtos.requests.auth.ResetPasswordRequest;
import com.code.salesappbackend.dtos.requests.auth.UserRegisterDto;
import com.code.salesappbackend.dtos.requests.auth.VerifyEmailDto;
import com.code.salesappbackend.dtos.responses.auth.LoginResponse;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.auth.Token;
import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.models.auth.UserDetail;
import com.code.salesappbackend.models.enums.Role;
import com.code.salesappbackend.repositories.auth.TokenRepository;
import com.code.salesappbackend.repositories.user.UserRepository;
import com.code.salesappbackend.services.interfaces.auth.AuthService;
import com.code.salesappbackend.services.interfaces.user.EmailService;
import com.code.salesappbackend.services.interfaces.auth.JwtService;
import com.code.salesappbackend.services.interfaces.auth.TokenService;
import com.code.salesappbackend.utils.EmailDetails;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;

    @Override
    public void register(UserRegisterDto userRegisterDto) throws DataExistsException, MessagingException {
        User user = mapToUser(userRegisterDto);
        userRepository.save(user);
        EmailDetails emailDetails = EmailDetails.builder()
                .msgBody(user.getOtp())
                .subject("Mã otp xác thực email tên shop")
                .recipient(user.getEmail())
                .build();
        emailService.sendHtmlMail(emailDetails);

    }

    @Override
    public LoginResponse login(LoginRequestDto loginRequestDto) throws DataNotFoundException {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("email is not exist"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        UserDetail userDetail = new UserDetail(user);

        Token token = new Token();
        token.setAccessToken(jwtService.generateToken(userDetail));
        token.setRefreshToken(jwtService.generateRefreshToken(new HashMap<>(), userDetail));
        token.setUser(user);
        token.setExpiredDate(LocalDateTime.now());
        tokenService.saveToken(user, token);
        return LoginResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();

    }

    @Override
    public LoginResponse verifyEmail(VerifyEmailDto verifyEmailDto) throws DataNotFoundException {
        String email = verifyEmailDto.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("email is not exist"));
        if(verifyEmailDto.getOtp().equals(user.getOtp())) {
            user.setVerify(true);
            userRepository.save(user);
        } else {
            throw new DataNotFoundException("OTP is not correct");
        }
        UserDetail userDetail = new UserDetail(user);
        Token token = new Token();
        token.setAccessToken(jwtService.generateToken(userDetail));
        token.setRefreshToken(jwtService.generateRefreshToken(new HashMap<>(), userDetail));
        token.setUser(user);
        token.setExpiredDate(LocalDateTime.now());
        tokenService.saveToken(user, token);
        return LoginResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) throws DataNotFoundException {
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new DataNotFoundException("refreshToken is not exist"));
        String email = jwtService.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        UserDetail userDetail = new UserDetail(user);
        if(!jwtService.validateRefreshToken(refreshToken, userDetail)) {
            tokenRepository.delete(token);
            throw new DataNotFoundException("refresh token is incorrect");
        }
        token.setAccessToken(jwtService.generateToken(userDetail));
        token.setExpiredDate(LocalDateTime.now());
        tokenRepository.save(token);

        return LoginResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void sendVerificationEmail(String email) throws MessagingException, DataNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("email not found"));
        user.setOtpResetPassword(getOtp());
        userRepository.save(user);
        EmailDetails emailDetails = EmailDetails.builder()
                .msgBody(user.getOtpResetPassword())
                .subject("Mã otp đặt lại mật khẩu")
                .recipient(user.getEmail())
                .build();
        emailService.sendHtmlMail(emailDetails);
    }

    @Override
    public LoginResponse resetPassword(ResetPasswordRequest resetPasswordRequest) throws DataNotFoundException {
        User user = userRepository.findByEmail(resetPasswordRequest.getEmail())
                .orElseThrow(() -> new DataNotFoundException("email not found"));
        if(user.getOtpResetPassword() != null &&
                user.getOtpResetPassword().equals(resetPasswordRequest.getOtpResetPassword())) {
            user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
            user.setOtpResetPassword(null);
            userRepository.save(user);
            List<Token> tokens = tokenRepository.findAllByUserOrderByExpiredDateDesc(user);
            if(!tokens.isEmpty()) {
                tokenRepository.deleteAll(tokens);
            }
            UserDetail userDetail = new UserDetail(user);
            Token newToken = new Token();
            newToken.setUser(user);
            newToken.setAccessToken(jwtService.generateToken(userDetail));
            newToken.setRefreshToken(jwtService.generateRefreshToken(new HashMap<>(), userDetail));
            newToken.setExpiredDate(LocalDateTime.now());
            tokenRepository.save(newToken);
            return LoginResponse.builder()
                    .accessToken(newToken.getAccessToken())
                    .refreshToken(newToken.getRefreshToken())
                    .build();
        }
        throw new DataNotFoundException("OTP is not correct");
    }

    @Override
    public void verificationEmailForResetPassword(VerifyEmailDto verifyEmailDto) throws DataNotFoundException {
        String email = verifyEmailDto.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("email is not exist"));
        if(verifyEmailDto.getOtp().equals(user.getOtpResetPassword())) {
            user.setVerify(true);
            userRepository.save(user);
        } else {
            throw new DataNotFoundException("OTP is not correct");
        }
    }

    @Override
    public void resendVerificationEmail(String email) throws MessagingException, DataNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("email is not exist"));
        user.setOtp(getOtp());
        userRepository.save(user);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Mã otp xác thực tên shop")
                .msgBody(user.getOtp())
                .build();
        emailService.sendHtmlMail(emailDetails);
    }

    @Override
    public void logout(String accessToken) throws DataNotFoundException {
        Token token = tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new DataNotFoundException("token not found"));
        tokenRepository.delete(token);
    }


    private User mapToUser(UserRegisterDto userRegisterDto) throws DataExistsException {
        Optional<User> user = userRepository.findByEmail(userRegisterDto.getEmail());
        User userExists = new User();
        if(user.isPresent()) {
            if(user.get().isVerify()) {
                throw new DataExistsException("email already exists");
            }
            userExists = user.get();
        }
        String otp = getOtp();
        User userRs = User.builder()
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .name(userRegisterDto.getName())
                .role(Role.ROLE_USER)
                .otp(otp)
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .build();
        userRs.setId(userExists.getId());
        return userRs;
    }

    private String getOtp() {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);
        return String.valueOf(randomNumber);
    }
}
