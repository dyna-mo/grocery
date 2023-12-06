package com.example.grocery.controller;

import com.example.grocery.model.CartItem;
import com.example.grocery.model.User;
import com.example.grocery.model.Order;
import com.example.grocery.model.Product;
import com.example.grocery.service.OrderService;
import com.example.grocery.service.ProductCatalogService;
import com.example.grocery.service.ShoppingCartService;
import com.example.grocery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final ProductCatalogService productCatalogService;

    private final ShoppingCartService shoppingCartService;

    private final UserService userService;

    private final OrderService orderService;

    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productCatalogService.getAllProducts());
    }

    @PostMapping("/cart/items")
    public ResponseEntity<CartItem> addToCart(@RequestBody CartItem cartItem) {
        return ResponseEntity.ok(shoppingCartService.addToCart(cartItem));
    }

    @GetMapping("/cart/{cartId}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        return ResponseEntity.ok(shoppingCartService.getCartItems(cartId));
    }

    @PutMapping("/cart/items")
    public ResponseEntity<CartItem> updateCart(@RequestBody CartItem cartItem) {
        return ResponseEntity.ok(shoppingCartService.updateCart(cartItem));
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.placeOrder(order));
    }
}
