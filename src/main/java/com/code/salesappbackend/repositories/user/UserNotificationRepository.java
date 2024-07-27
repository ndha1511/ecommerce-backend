package com.code.salesappbackend.repositories.user;

import com.code.salesappbackend.models.user.UserNotification;
import com.code.salesappbackend.models.id_classes.UserNotificationId;
import com.code.salesappbackend.repositories.BaseRepository;

import java.util.List;

public interface UserNotificationRepository extends BaseRepository<UserNotification, UserNotificationId> {
    List<UserNotification> findAllByNotificationId(Long id);
}