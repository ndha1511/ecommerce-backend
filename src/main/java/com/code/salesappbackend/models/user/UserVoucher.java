package com.code.salesappbackend.models.user;

import com.code.salesappbackend.models.id_classes.UserVoucherId;
import com.code.salesappbackend.models.voucher.Voucher;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_vouchers")
@IdClass(UserVoucherId.class)
public class UserVoucher {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
    @Column(name = "is_used")
    private boolean isUsed;
}
