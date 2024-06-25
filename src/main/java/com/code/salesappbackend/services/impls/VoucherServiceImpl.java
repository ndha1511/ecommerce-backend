package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.Voucher;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.services.interfaces.VoucherService;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl extends BaseServiceImpl<Voucher, Long> implements VoucherService {
    public VoucherServiceImpl(BaseRepository<Voucher, Long> repository) {
        super(repository, Voucher.class);
    }
}
