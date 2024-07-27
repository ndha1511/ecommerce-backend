package com.code.salesappbackend.services.impls.user;

import com.code.salesappbackend.models.user.UserVoucher;
import com.code.salesappbackend.models.id_classes.UserVoucherId;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.user.UserVoucherService;
import org.springframework.stereotype.Service;

@Service
public class UserVoucherImpl extends BaseServiceImpl<UserVoucher, UserVoucherId> implements UserVoucherService {
    public UserVoucherImpl(BaseRepository<UserVoucher, UserVoucherId> repository) {
        super(repository, UserVoucher.class);
    }
}
