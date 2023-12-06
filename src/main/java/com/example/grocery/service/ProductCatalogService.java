package com.example.grocery.service;

import com.example.grocery.exception.NotFoundException;
import com.example.grocery.model.Inventory;
import com.example.grocery.model.Product;
import com.example.grocery.repository.InventoryRepository;
import com.example.grocery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCatalogService {
    public static final long INITIAL_COUNT = 0l;

    private final ProductRepository productRepository;

    private final InventoryRepository inventoryRepository;

    @Transactional
    public Product addProduct(Product product) {
        Product newProduct = productRepository.save(product);

        Inventory inventory = new Inventory(newProduct.getId(), INITIAL_COUNT);
        inventoryRepository.save(inventory);

        return newProduct;
    }

    @Transactional
    public List<Product> addProducts(List<Product> products) {
        List<Product> newProducts = productRepository.saveAll(products);

        List<Inventory> inventories = new ArrayList<>();

        for(Product product : newProducts) {
            Inventory inventory = new Inventory(product.getId(), INITIAL_COUNT);
            inventories.add(inventory);
        }

        inventoryRepository.saveAll(inventories);

        return newProducts;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product product) {
        if (isPresent(product.getId())) {
            return productRepository.save(product);
        } else {
            throw new NotFoundException("product not found");
        }
    }

    @Transactional
    public void deleteProduct(Long productId) {
       if(isPresent(productId)) {
           inventoryRepository.deleteById(productId);
           productRepository.deleteById(productId);
       } else {
           throw new NotFoundException("product not found");
       }
    }

    private boolean isPresent(Long productId) {
        Optional<Product> existingProductOptional = productRepository.findById(productId);
        return existingProductOptional.isPresent();
    }
}
