package com.code.salesappbackend.models;

import com.code.salesappbackend.models.id_classes.UserVoucherId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
