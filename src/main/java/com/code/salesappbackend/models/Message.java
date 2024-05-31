package com.code.salesappbackend.models;

import com.code.salesappbackend.models.enums.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @Column(name = "message_id")
    private String id;
    @Column(nullable = false)
    private String sender;
    @Column(nullable = false)
    private String receiver;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomChat roomChat;
    @Column(name = "send_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sendDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType;
}
