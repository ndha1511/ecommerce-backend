package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Token;
import com.code.salesappbackend.models.User;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends BaseRepository<Token, Long> {
    List<Token> findAllByUserOrderByExpiredDateDesc(User user);
    boolean existsByAccessToken(String accessToken);
    boolean existsByRefreshToken(String refreshToken);
    Optional<Token> findByRefreshToken(String refreshToken);
    Optional<Token> findByAccessToken(String accessToken);
}