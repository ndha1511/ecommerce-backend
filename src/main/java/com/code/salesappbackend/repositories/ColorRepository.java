package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends BaseRepository<Color, Long> {
    boolean existsByColorName(String colorName);
}