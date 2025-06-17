package com.ayrotek.tax_calculator.controller;

import com.ayrotek.tax_calculator.model.Product;
import com.ayrotek.tax_calculator.model.User;
import com.ayrotek.tax_calculator.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products") // Base URL for the Product API
@RequiredArgsConstructor
public class ProductController {

    // Controller Layer (API) Handles HTTP requests and responses

    private final ProductService productService;

    //Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    //Create a new product and a dummy user
    @PostMapping
    //request body converts json into product obj
    public Product createProduct(@RequestBody Product product) {
        User dummyUser = new User(1L, "testuser", "password");
        return productService.createProduct(product, dummyUser);
    }

    //calculate tax for a specific product by ID

    @GetMapping("/{id}/tax")
    public double calculateTax(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(productService::calculateTax) // Calculate tax if exists
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));
    }
}
