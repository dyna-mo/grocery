package com.example.grocery.repository;

import com.example.grocery.model.CartItem;
import com.example.grocery.model.compositekey.CartItemPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemPrimaryKey> {
    List<CartItem> findAllByCartId(Long cartId);
}
