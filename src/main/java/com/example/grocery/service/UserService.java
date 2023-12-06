package com.example.grocery.service;

import com.example.grocery.model.Cart;
import com.example.grocery.model.User;
import com.example.grocery.repository.CartRepository;
import com.example.grocery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    @Transactional
    public User createUser(User user) {
        Cart cart = cartRepository.save(new Cart());

        user.setActiveCartId(cart.getId());

        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public void updateActiveCartAfterOrder(Long userId) {
        Optional<User> optional = userRepository.findById(userId);

        User user = optional.get();

        Cart cart = cartRepository.save(new Cart());

        user.setActiveCartId(cart.getId());

        userRepository.save(user);
    }
}
