package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.Notification;
import com.code.salesappbackend.models.User;
import com.code.salesappbackend.models.Voucher;
import com.code.salesappbackend.repositories.*;
import com.code.salesappbackend.services.interfaces.NotificationService;
import com.code.salesappbackend.services.interfaces.VoucherService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherServiceImpl extends BaseServiceImpl<Voucher, Long> implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherUsagesRepository voucherUsagesRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public VoucherServiceImpl(BaseRepository<Voucher, Long> repository,
                              VoucherRepository voucherRepository,
                              VoucherUsagesRepository voucherUsagesRepository,
                              UserRepository userRepository,
                              NotificationService notificationService) {
        super(repository, Voucher.class);
        this.voucherRepository = voucherRepository;
        this.voucherUsagesRepository = voucherUsagesRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Voucher save(Voucher voucher) {
        Voucher voucherSaved = super.save(voucher);
        // notify
        Notification notification = new Notification();
        notification.setScope(voucher.getScope());
        notification.setNotificationDate(LocalDateTime.now());
        String content = String.format("Bạn nhận được voucher giảm %s%%", voucher.getDiscount() * 100);
        notification.setContent(content);
        notificationService.save(notification);
        notificationService.sendNotification(notification);
        return voucherSaved;
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
