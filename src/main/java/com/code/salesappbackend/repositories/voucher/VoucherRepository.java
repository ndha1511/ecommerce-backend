package com.code.salesappbackend.repositories.voucher;

import com.code.salesappbackend.models.voucher.Voucher;
import com.code.salesappbackend.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherRepository extends BaseRepository<Voucher, Long> {
    @Query(value = "select v from Voucher  v left join UserVoucher uv on v = uv.voucher and uv.isUsed = false " +
            "where (uv.user = (select u from User u where u.email like :email) or v.scope = 'ALL') " +
            "and v.expiredDate > current date")
    List<Voucher> findVouchersByEmail(String email);
}