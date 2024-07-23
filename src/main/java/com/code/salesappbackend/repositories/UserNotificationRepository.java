package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.UserNotification;
import com.code.salesappbackend.models.id_classes.UserNotificationId;

import java.util.List;

public interface UserNotificationRepository extends BaseRepository<UserNotification, UserNotificationId> {
    List<UserNotification> findAllByNotificationId(Long id);
}