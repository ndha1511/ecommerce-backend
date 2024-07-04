package com.code.salesappbackend.models;

import com.code.salesappbackend.models.enums.Gender;
import com.code.salesappbackend.models.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(nullable = false, name = "phone_number",
            length = 10)
    @JsonIgnore
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    private boolean verify;
    private String otp;
    @Column(name = "otp_reset_password")
    private String otpResetPassword;
    @Column(name = "facebook_account_id")
    private String facebookAccountId;
    @Column(name = "google_account_id")
    private String googleAccountId;
    @Column(name = "avatar_url")
    private String avatarUrl;
}
