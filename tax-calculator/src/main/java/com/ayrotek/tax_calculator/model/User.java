package com.ayrotek.tax_calculator.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Username;

    private String Password;
}
