package com.ayrotek.tax_calculator.repository;
import com.ayrotek.tax_calculator.model.Product;

//Data Access Layer (Database)

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOwnerId(Long ownerId);
    
}
