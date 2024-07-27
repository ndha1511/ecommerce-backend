package com.code.salesappbackend.repositories.product;

import com.code.salesappbackend.models.product.Color;
import com.code.salesappbackend.repositories.BaseRepository;

public interface ColorRepository extends BaseRepository<Color, Long> {
    boolean existsByColorName(String colorName);
}