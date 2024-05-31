package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Long> {
}