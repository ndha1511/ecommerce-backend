package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.User;
import com.code.salesappbackend.models.Voucher;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.UserRepository;
import com.code.salesappbackend.repositories.VoucherRepository;
import com.code.salesappbackend.repositories.VoucherUsagesRepository;
import com.code.salesappbackend.services.interfaces.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherServiceImpl extends BaseServiceImpl<Voucher, Long> implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherUsagesRepository voucherUsagesRepository;
    private final UserRepository userRepository;

    public VoucherServiceImpl(BaseRepository<Voucher, Long> repository, VoucherRepository voucherRepository, VoucherUsagesRepository voucherUsagesRepository, UserRepository userRepository) {
        super(repository, Voucher.class);
        this.voucherRepository = voucherRepository;
        this.voucherUsagesRepository = voucherUsagesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Voucher> getVouchersByEmail(String email) throws DataNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );
        return voucherRepository.findVouchersByEmail(email).stream()
                .filter(voucher ->
                        !voucherUsagesRepository
                        .existsByVoucherIdAndUserId(voucher.getId(), user.getId()))
                .toList();
    }
}
