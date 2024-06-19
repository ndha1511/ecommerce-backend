package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends BaseRepository<Size, Long> {
    boolean existsByTextSize(String textSize);
    boolean existsByNumberSize(Short numberSize);
}