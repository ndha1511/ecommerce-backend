package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.VoucherUsages;
import com.code.salesappbackend.models.id_classes.UserVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherUsagesRepository extends BaseRepository<VoucherUsages, UserVoucherId> {
}