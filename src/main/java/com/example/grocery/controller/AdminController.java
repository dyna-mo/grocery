package com.example.grocery.controller;

import com.example.grocery.model.Inventory;
import com.example.grocery.model.Product;
import com.example.grocery.service.InventoryManagementService;
import com.example.grocery.service.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ProductCatalogService productCatalogService;

    private final InventoryManagementService inventoryManagementService;

    @PostMapping("/products")
    public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products) {
        return ResponseEntity.ok(productCatalogService.addProducts(products));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productCatalogService.getAllProducts());
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws Exception {
        return ResponseEntity.ok(productCatalogService.updateProduct(product));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productCatalogService.deleteProduct(productId);
        return ResponseEntity.ok("deletion success");
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<Inventory>> getInventory() {
        return ResponseEntity.ok(inventoryManagementService.getAllInventory());
    }

    @PutMapping("/inventory")
    public ResponseEntity<Inventory> updateInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryManagementService.updateInventory(inventory));
    }
}
