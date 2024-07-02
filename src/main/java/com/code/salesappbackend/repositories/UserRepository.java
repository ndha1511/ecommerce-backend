package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}