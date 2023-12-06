package com.example.grocery.service;

import com.example.grocery.exception.NotFoundException;
import com.example.grocery.model.CartItem;
import com.example.grocery.model.Inventory;
import com.example.grocery.model.Product;
import com.example.grocery.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryManagementService {
    private final InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
       return inventoryRepository.findAll();
    }

    public Inventory updateInventory(Inventory inventory) {
        if(isPresent(inventory.getProductId())){
            return inventoryRepository.save(inventory);
        } else {
            throw new NotFoundException("inventory not found");
        }
    }

    private boolean isPresent(Long inventoryId) {
        Optional<Inventory> existingProductOptional = inventoryRepository.findById(inventoryId);
        return existingProductOptional.isPresent();
    }

    public boolean availabilityCheck(List<CartItem> cartItems) {
        List<Long> productIds = cartItems.stream().map(CartItem::getProductId).collect(Collectors.toList());
        Map<Long, Long> inventoryMap = getInventoryCountForProducts(productIds);

        for(CartItem cartItem : cartItems) {
            if(cartItem.getCount() > inventoryMap.get(cartItem.getProductId())) {
                return false;
            }
        }

        return true;
    }

    private Map<Long, Long> getInventoryCountForProducts(List<Long> productIds) {
        List<Inventory> inventory = inventoryRepository.findAllById(productIds);
        Map<Long, Long> inventoryCountMap = inventory.stream().collect(Collectors.toMap(Inventory::getProductId, Inventory::getCount));
        return inventoryCountMap;
    }

    private Map<Long, Inventory> getInventoryForProducts(List<Long> productIds) {
        List<Inventory> inventory = inventoryRepository.findAllById(productIds);
        Map<Long, Inventory> inventoryMap = inventory.stream().collect(Collectors.toMap(Inventory::getProductId, obj->obj));
        return inventoryMap;
    }

    public boolean updateInventoryForOrder(List<CartItem> cartItems) {
        List<Long> productIds = cartItems.stream().map(CartItem::getProductId).collect(Collectors.toList());
        Map<Long, Inventory> inventoryMap = getInventoryForProducts(productIds);

        List<Inventory> updatedInventory = new ArrayList<>();
        for(CartItem cartItem : cartItems) {
            Inventory inventory = inventoryMap.get(cartItem.getProductId());
            if(cartItem.getCount() > inventory.getCount()) {
                return false;
            }
            inventory.setCount(inventory.getCount() - cartItem.getCount());
            updatedInventory.add(inventory);
        }

        inventoryRepository.saveAll(updatedInventory);

        return true;
    }
}
