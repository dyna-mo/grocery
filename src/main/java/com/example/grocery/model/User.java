package com.example.grocery.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String address;

    @Column(unique = true, nullable = false)
    private String email;

    private Long activeCartId;
}
