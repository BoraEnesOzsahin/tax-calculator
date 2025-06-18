package com.ayrotek.tax_calculator.service;

import com.ayrotek.tax_calculator.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductServiceTest {

    ProductService productService = new ProductService(null); // Rep not needed for this test

    @Test
    void testCalculateTax() {
        Product p = new Product();
        p.setPrice(1000.0);
        p.setTaxRate(0.18);

        double tax = productService.calculateTax(p);

        assertEquals(180.0, tax);
    }
}
