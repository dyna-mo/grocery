package com.example.grocery.service;

import com.example.grocery.exception.NotFoundException;
import com.example.grocery.model.CartItem;
import com.example.grocery.model.Order;
import com.example.grocery.model.User;
import com.example.grocery.model.compositekey.CartItemPrimaryKey;
import com.example.grocery.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final CartItemRepository cartItemRepository;

    private final UserService userService;

    public CartItem addToCart(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItems(Long cartId) {
        return cartItemRepository.findAllByCartId(cartId);
    }

    public CartItem updateCart(CartItem cartItem) {
        if (isPresent(cartItem)) {
            return cartItemRepository.save(cartItem);
        } else {
            throw new NotFoundException("cart item not found");
        }
    }

    private boolean isPresent(CartItem cartItem) {
        CartItemPrimaryKey id = new CartItemPrimaryKey(cartItem.getCartId(), cartItem.getProductId());
        Optional<CartItem> existingProductOptional = cartItemRepository.findById(id);
        return existingProductOptional.isPresent();
    }

    public Boolean userActiveCartCheck(Order order) {
        Optional<User> user = userService.getUserById(order.getUserId());

        if(user.isPresent() && user.get().getActiveCartId().equals(order.getCartId())) {
            return true;
        } else {
            return false;
        }
    }
}
