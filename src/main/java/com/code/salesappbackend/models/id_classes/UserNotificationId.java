package com.code.salesappbackend.models.id_classes;

import com.code.salesappbackend.models.Notification;
import com.code.salesappbackend.models.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class UserNotificationId {
    private User user;
    private Notification notification;
}
