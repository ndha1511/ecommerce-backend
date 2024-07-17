package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.Voucher;

import java.util.List;

public interface VoucherService extends BaseService<Voucher, Long> {
    List<Voucher> getVouchersByEmail(String email) throws DataNotFoundException;
}
