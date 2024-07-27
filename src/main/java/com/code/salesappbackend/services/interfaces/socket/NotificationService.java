package com.code.salesappbackend.services.interfaces.socket;

import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.models.socket.Notification;
import com.code.salesappbackend.services.interfaces.BaseService;

import java.util.List;

public interface NotificationService extends BaseService<Notification, Long> {
    void sendNotification(Notification notification);
    PageResponse<List<Notification>> getNotificationsByUserId(Long userId, int pageNo, int pageSize);
}
