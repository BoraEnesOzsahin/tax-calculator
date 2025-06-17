package com.ayrotek.tax_calculator.repository;

import com.ayrotek.tax_calculator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//Data Access Layer (Database)

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    
}



