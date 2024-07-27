package com.code.salesappbackend.models.voucher;

import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.models.id_classes.UserVoucherId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "voucher_usages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserVoucherId.class)
public class VoucherUsages {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
    @Column(name = "usages_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime usagesDate;
}
