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
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    @Bean
    // Run at startup
    public CommandLineRunner loadData(){
        
        return args -> {

        addUserIfNotExists("testuser", "password");
        addUserIfNotExists("secuser", "secpass");
        addUserIfNotExists("theuser", "thepass");


        System.out.println(" Başlangıç kullanıcıları oluşturuldu.");
    };
        
        };
    


    private void addUserIfNotExists(String username, String rawPassword) {
    if (userRepository.findByUsername(username).isEmpty()) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
        System.out.println(" Kullanıcı '" + username + "' eklendi.");
    }

}
    
}
