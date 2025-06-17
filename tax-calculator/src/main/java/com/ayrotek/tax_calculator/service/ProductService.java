package com.ayrotek.tax_calculator.service;

import com.ayrotek.tax_calculator.model.Product;
import com.ayrotek.tax_calculator.model.User;
import com.ayrotek.tax_calculator.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service  // Business logic and method layer
@RequiredArgsConstructor

public class ProductService {
    
    private final ProductRepository productRepository;

    public Product createProduct(Product product, User owner){
        product.setOwner(owner);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();

    }

    //Put optional bcs if none product found no error will be thrown
    public Optional<Product> getProductById(Long id) { 
        return productRepository.findById(id);
    }

    //Thx to lombok, we don't need to write getters and setters 
    //that's why we can use getPrice() and getTaxRate() directly

    public double calculateTax(Product product) {
        return product.getPrice() * product.getTaxRate();
    }

    public boolean isOwner(User user, Product product) {
        return product.getOwner().getId().equals(user.getId());
    }
}   



