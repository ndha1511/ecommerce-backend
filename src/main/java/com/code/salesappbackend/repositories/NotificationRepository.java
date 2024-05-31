package com.code.salesappbackend.repositories;

import com.code.salesappbackend.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}