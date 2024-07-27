package com.code.salesappbackend.services.impls.user;

import com.code.salesappbackend.dtos.requests.auth.ChangePasswordRequest;
import com.code.salesappbackend.dtos.responses.auth.LoginResponse;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.auth.Token;
import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.models.auth.UserDetail;
import com.code.salesappbackend.models.enums.Role;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.auth.TokenRepository;
import com.code.salesappbackend.repositories.user.UserRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.auth.JwtService;
import com.code.salesappbackend.services.interfaces.user.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public UserServiceImpl(BaseRepository<User, Long> repository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           TokenRepository tokenRepository,
                           JwtService jwtService) {
        super(repository, User.class);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
    }


    @Override
    public LoginResponse changePassword(ChangePasswordRequest changePasswordRequest) throws DataNotFoundException {
        User user = userRepository.findByEmail(changePasswordRequest.getEmail())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        boolean isValidOldPassword = passwordEncoder.matches(changePasswordRequest.getOldPassword(),
                user.getPassword());
        if(!isValidOldPassword) {
            throw new DataNotFoundException("password does not match");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
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

    @Override
    public User getUserByEmail(String email) throws DataNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("user not found"));
    }

    @PostConstruct
    public void createAdminAccount() {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setName("admin");
        user.setRole(Role.ROLE_ADMIN);
        user.setVerify(true);
        user.setPhoneNumber("");
        if(!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }
}
