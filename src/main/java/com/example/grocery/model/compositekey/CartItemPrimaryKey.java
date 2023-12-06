package com.example.grocery.model.compositekey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemPrimaryKey implements Serializable {
    private Long cartId;

    private Long productId;
}
