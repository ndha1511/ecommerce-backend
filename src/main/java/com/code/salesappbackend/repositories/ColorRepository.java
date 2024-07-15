package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Color;

public interface ColorRepository extends BaseRepository<Color, Long> {
    boolean existsByColorName(String colorName);
}