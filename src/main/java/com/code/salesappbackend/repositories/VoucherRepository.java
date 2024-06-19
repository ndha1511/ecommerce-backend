package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends BaseRepository<Voucher, Long> {
}