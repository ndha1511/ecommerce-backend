package com.code.salesappbackend.repositories.user;

import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}