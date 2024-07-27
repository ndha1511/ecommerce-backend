package com.code.salesappbackend.models.socket;

import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.models.id_classes.UserNotificationId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification_read")
@IdClass(UserNotificationId.class)
public class NotificationRead {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

}
