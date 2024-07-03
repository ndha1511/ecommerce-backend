package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.dtos.requests.ChangePasswordRequest;
import com.code.salesappbackend.dtos.responses.LoginResponse;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.Token;
import com.code.salesappbackend.models.User;
import com.code.salesappbackend.models.UserDetail;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.TokenRepository;
import com.code.salesappbackend.repositories.UserRepository;
import com.code.salesappbackend.services.interfaces.JwtService;
import com.code.salesappbackend.services.interfaces.UserService;
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
}
