package com.example.grocery.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_table", indexes = {@Index(name = "idx_user_id_search", columnList = "user_id")})
public class Order {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long cartId;
}
