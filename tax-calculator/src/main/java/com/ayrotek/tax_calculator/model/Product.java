package com.ayrotek.tax_calculator.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor                 

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Double price;
    private Double taxRate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
