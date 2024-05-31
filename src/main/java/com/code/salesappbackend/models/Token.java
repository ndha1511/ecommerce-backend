package com.code.salesappbackend.models;

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
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;
    @Column(name = "access_token", nullable = false, unique = true)
    private String accessToken;
    @Column(name = "refresh_token", nullable = false, unique = true)
    private String refreshToken;
    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiredDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
