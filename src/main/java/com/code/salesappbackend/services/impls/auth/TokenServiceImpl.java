package com.code.salesappbackend.services.impls.auth;

import com.code.salesappbackend.models.auth.Token;
import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.repositories.auth.TokenRepository;
import com.code.salesappbackend.services.interfaces.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public void saveToken(User user, Token token) {
        List<Token> tokens = tokenRepository.findAllByUserOrderByExpiredDateDesc(user);
        if(!tokens.isEmpty() && tokens.size() >= 2) {
            Token tokenDelete = tokens.get(tokens.size() - 1);
            tokenRepository.delete(tokenDelete);
        }
        tokenRepository.save(token);
    }
}
