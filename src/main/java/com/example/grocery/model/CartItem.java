package com.example.grocery.model;

import com.example.grocery.model.compositekey.CartItemPrimaryKey;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(CartItemPrimaryKey.class)
public class CartItem {
    @Id
    @Column(nullable = false)
    private Long cartId;

    @Id
    @Column(nullable = false)
    private Long productId;

    private Long count;
}
