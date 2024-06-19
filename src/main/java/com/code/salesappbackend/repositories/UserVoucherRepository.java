package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.UserVoucher;
import com.code.salesappbackend.models.id_classes.UserVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVoucherRepository extends BaseRepository<UserVoucher, UserVoucherId> {
}