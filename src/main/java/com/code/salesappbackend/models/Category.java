package com.code.salesappbackend.models;

import com.code.salesappbackend.models.enums.Status;
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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    @Column(nullable = false, name = "category_name", unique = true)
    private String categoryName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "category_status")
    private Status status;
}
