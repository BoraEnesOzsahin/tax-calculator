package com.ayrotek.tax_calculator.config;


import com.ayrotek.tax_calculator.model.User;
import com.ayrotek.tax_calculator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@RequiredArgsConstructor

public class DataInitializer {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    
    @Bean
    // Run at startup
    public CommandLineRunner loadData(){
        return args -> {
            // Check if the user exists
            if(userRepository.findByUsername("testuser").isEmpty()){
                User user = new User();
                user.setUsername("testuser");
                user.setPassword(passwordEncoder.encode("password"));
                userRepository.save(user); // add user to the database
                System.out.println("Test user veritabanına eklendi.");
         }
        };
    }
}
