package com.code.salesappbackend.models.socket;

import com.code.salesappbackend.models.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message implements Serializable {
    @Id
    @Column(name = "message_id")
    private String id;
    @Column(nullable = false)
    private String sender;
    @Column(nullable = false)
    private String receiver;
    @Column(name = "room_id")
    private String roomId;
    @Column(name = "send_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType;
    private String path;
    private String content;
}
