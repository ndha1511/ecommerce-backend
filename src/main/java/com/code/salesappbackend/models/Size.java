package com.code.salesappbackend.models;

import com.code.salesappbackend.models.enums.SizeType;
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
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "size_type")
    private SizeType sizeType;
    @Column(name = "number_size", unique = true)
    private Short numberSize;
    @Column(name = "text_size", length = 10, unique = true)
    private String textSize;
}
