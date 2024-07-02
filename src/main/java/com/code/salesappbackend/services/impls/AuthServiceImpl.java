package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.dtos.requests.LoginRequestDto;
import com.code.salesappbackend.dtos.requests.UserRegisterDto;
import com.code.salesappbackend.dtos.requests.VerifyEmailDto;
import com.code.salesappbackend.dtos.responses.LoginResponse;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.User;
import com.code.salesappbackend.models.UserDetail;
import com.code.salesappbackend.models.enums.Role;
import com.code.salesappbackend.repositories.UserRepository;
import com.code.salesappbackend.services.interfaces.AuthService;
import com.code.salesappbackend.services.interfaces.EmailService;
import com.code.salesappbackend.services.interfaces.JwtService;
import com.code.salesappbackend.utils.EmailDetails;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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

        return LoginResponse.builder()
                .accessToken(jwtService.generateToken(userDetail))
                .refreshToken("abcd")
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
        return LoginResponse.builder()
                .accessToken(jwtService.generateToken(userDetail))
                .refreshToken("abcd")
                .build();
    }

    private User mapToUser(UserRegisterDto userRegisterDto) throws DataExistsException {
        if(userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new DataExistsException("email already exist");
        }
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);
        String otp = String.valueOf(randomNumber);
        return User.builder()
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .name(userRegisterDto.getName())
                .role(Role.ROLE_USER)
                .otp(otp)
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .build();
    }
}
