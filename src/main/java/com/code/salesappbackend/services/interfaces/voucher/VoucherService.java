package com.code.salesappbackend.services.interfaces.voucher;

import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.voucher.Voucher;
import com.code.salesappbackend.services.interfaces.BaseService;

import java.util.List;

public interface VoucherService extends BaseService<Voucher, Long> {
    List<Voucher> getVouchersByEmail(String email) throws DataNotFoundException;
}
