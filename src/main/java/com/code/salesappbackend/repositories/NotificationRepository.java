package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends BaseRepository<Notification, Long> {
    @Query(value = "select n from Notification n " +
            "left join UserNotification un " +
            "on n = un.notification " +
            "where un.user.id = :userId or n.scope = 'ALL'")
    Page<Notification> getPageNotificationsByUserId(Long userId, Pageable pageable);
}