package com.code.salesappbackend.repositories.auth;

import com.code.salesappbackend.models.auth.Token;
import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends BaseRepository<Token, Long> {
    List<Token> findAllByUserOrderByExpiredDateDesc(User user);
    boolean existsByAccessToken(String accessToken);
    boolean existsByRefreshToken(String refreshToken);
    Optional<Token> findByRefreshToken(String refreshToken);
    Optional<Token> findByAccessToken(String accessToken);
}