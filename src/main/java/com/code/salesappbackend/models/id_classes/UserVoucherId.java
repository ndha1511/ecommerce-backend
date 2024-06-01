package com.code.salesappbackend.models.id_classes;

import com.code.salesappbackend.models.User;
import com.code.salesappbackend.models.Voucher;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serializable;


@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class UserVoucherId implements Serializable {
    private User user;
    private Voucher voucher;

}
