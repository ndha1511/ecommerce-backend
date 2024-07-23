package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.models.Notification;

import java.util.List;

public interface NotificationService extends BaseService<Notification, Long>{
    void sendNotification(Notification notification);
    PageResponse<List<Notification>> getNotificationsByUserId(Long userId, int pageNo, int pageSize);
}
