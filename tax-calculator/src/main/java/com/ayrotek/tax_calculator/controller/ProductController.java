package com.ayrotek.tax_calculator.controller;

import com.ayrotek.tax_calculator.model.Product;
import com.ayrotek.tax_calculator.model.User;
import com.ayrotek.tax_calculator.repository.UserRepository;
import com.ayrotek.tax_calculator.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products") // Base URL for the Product API
@RequiredArgsConstructor
public class ProductController {

    // Controller Layer (API) Handles HTTP requests and responses

    private final ProductService productService;
    private final UserRepository userRepository;

    //Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    //request body converts json into product obj
    public Product createProduct(@RequestBody Product product,
                        @RequestHeader("Authorization") String authHeader) {
        String username = extractUsernameFromAuth(authHeader);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        return productService.createProduct(product, user);
    }

    //calculate tax for a specific product by ID

    @GetMapping("/{id}/tax")
    public double calculateTax(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(productService::calculateTax) // Calculate tax if product exists
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));
    }


    public String extractUsernameFromAuth(String authHeader) {
        // Extract the username from the Auth header
        String base64 = authHeader.substring("Basic ".length());
        String decoded = new String(java.util.Base64.getDecoder().decode(base64));
        return decoded.split(":")[0]; // Return the username part
    }




        // Update product (only owner can update)
        //fetch the product by ID from url
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                @RequestBody Product updatedProduct,
                                @RequestHeader("Authorization") String authHeader) {
        String username = extractUsernameFromAuth(authHeader);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Product existingProduct = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        // Owner control
        if (!existingProduct.getOwner().getUsername().equals(user.getUsername())) {
            throw new RuntimeException("Bu ürünü güncelleme yetkiniz yok.");
        }

        // Update the existing product
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setTaxRate(updatedProduct.getTaxRate());

        return productService.createProduct(existingProduct, user); // update = save
    }

    // delete product (only owner can delete)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id,
                            @RequestHeader("Authorization") String authHeader) {
        String username = extractUsernameFromAuth(authHeader);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        // Owner control
        if (!product.getOwner().getUsername().equals(user.getUsername())) {
            throw new RuntimeException("Bu ürünü silme yetkiniz yok.");
        }

        productService.deleteProduct(product);
    }

}
