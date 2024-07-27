package com.code.salesappbackend.repositories.product;

import com.code.salesappbackend.models.product.Size;
import com.code.salesappbackend.repositories.BaseRepository;

public interface SizeRepository extends BaseRepository<Size, Long> {
    boolean existsByTextSize(String textSize);
    boolean existsByNumberSize(Short numberSize);
}