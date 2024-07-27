package com.code.salesappbackend.repositories.voucher;

import com.code.salesappbackend.models.voucher.VoucherUsages;
import com.code.salesappbackend.models.id_classes.UserVoucherId;
import com.code.salesappbackend.repositories.BaseRepository;

public interface VoucherUsagesRepository extends BaseRepository<VoucherUsages, UserVoucherId> {
    boolean existsByVoucherIdAndUserId(Long voucherId, Long userId);
}