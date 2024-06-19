package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.UserNotification;
import com.code.salesappbackend.models.id_classes.UserNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends BaseRepository<UserNotification, UserNotificationId> {
}