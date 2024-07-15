package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Size;

public interface SizeRepository extends BaseRepository<Size, Long> {
    boolean existsByTextSize(String textSize);
    boolean existsByNumberSize(Short numberSize);
}