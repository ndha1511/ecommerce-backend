package com.code.salesappbackend.models.id_classes;

import com.code.salesappbackend.models.User;
import com.code.salesappbackend.models.Voucher;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;


import java.io.Serializable;


@AllArgsConstructor
@EqualsAndHashCode
public class UserVoucherId implements Serializable {
    private User user;
    private Voucher voucher;

}
