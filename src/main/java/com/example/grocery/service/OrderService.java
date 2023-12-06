package com.example.grocery.service;

import com.example.grocery.exception.InsufficientInventoryException;
import com.example.grocery.exception.NotFoundException;
import com.example.grocery.model.CartItem;
import com.example.grocery.model.Order;
import com.example.grocery.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final InventoryManagementService inventoryManagementService;

    private final UserService userService;

    private final ShoppingCartService cartService;

    public Order placeOrder(Order order) {
        // checking if cart belongs to user
        Boolean cartCheckPassed = cartService.userActiveCartCheck(order);

        if(!cartCheckPassed) {
            throw new NotFoundException("cart check failed, active cart does not belong to user");
        }

        // fetching cart items for order
        List<CartItem> cartItems = cartService.getCartItems(order.getCartId());

        // check inventory before placing order
        boolean inventoryCheckPassed = inventoryManagementService.availabilityCheck(cartItems);

        if(!inventoryCheckPassed) {
            throw new InsufficientInventoryException("inventory check failed");
        }

        Order placedOrder = placeOrderFinalStep(order, cartItems);

        // initializing new empty active cart for user
        userService.updateActiveCartAfterOrder(order.getUserId());

        return placedOrder;
    }

    @Transactional
    private Order placeOrderFinalStep(Order order, List<CartItem> cartItems) {
        inventoryManagementService.updateInventoryForOrder(cartItems);

        return orderRepository.save(order);
    }

}
