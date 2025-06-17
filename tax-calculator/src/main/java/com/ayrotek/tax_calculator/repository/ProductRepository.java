package com.ayrotek.tax_calculator.repository;
import com.ayrotek.tax_calculator.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOwnerId(Long ownerId);
    
}
