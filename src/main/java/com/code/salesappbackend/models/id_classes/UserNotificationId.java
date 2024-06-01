package com.code.salesappbackend.models.id_classes;

import com.code.salesappbackend.models.Notification;
import com.code.salesappbackend.models.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class UserNotificationId implements Serializable {
    private User user;
    private Notification notification;
}
