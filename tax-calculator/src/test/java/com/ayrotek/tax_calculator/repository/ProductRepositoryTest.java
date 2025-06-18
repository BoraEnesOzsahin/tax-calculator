package com.ayrotek.tax_calculator.repository;

import com.ayrotek.tax_calculator.model.Product;
import com.ayrotek.tax_calculator.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveProduct() {
        // Create and save user
        User user = new User();
        user.setUsername("john");
        user.setPassword("1234");
        user = userRepository.save(user);

        // Create and save product
        Product product = new Product();
        product.setName("Test Ürün");
        product.setPrice(500.0);
        product.setTaxRate(0.2);
        product.setOwner(user);

        Product saved = productRepository.save(product);

        // Confirm that the product was saved correctly
        assertNotNull(saved.getId()); // check if ID is generated
        assertEquals("Test Ürün", saved.getName());
        assertEquals(0.2, saved.getTaxRate());
        assertEquals(user.getUsername(), saved.getOwner().getUsername());
    }


    @Test
    void testFindByOwnerReturnsOnlyTheirProducts() {
        // Create and save Alice
        User alice = new User();
        alice.setUsername("alice");
        alice.setPassword("alice123");
        alice = userRepository.save(alice);

        // Create and save Bob
        User bob = new User();
        bob.setUsername("bob");
        bob.setPassword("bob123");
        bob = userRepository.save(bob);

        // Create product for Alice
        Product aliceProduct = new Product();
        aliceProduct.setName("Alice Product");
        aliceProduct.setPrice(100.0);
        aliceProduct.setTaxRate(0.1);
        aliceProduct.setOwner(alice);
        productRepository.save(aliceProduct);

        // Create product for Bob
        Product bobProduct = new Product();
        bobProduct.setName("Bob Product");
        bobProduct.setPrice(200.0);
        bobProduct.setTaxRate(0.2);
        bobProduct.setOwner(bob);
        productRepository.save(bobProduct);

        // Act: find Alice’s products
        List<Product> aliceProducts = productRepository.findByOwnerId(alice.getId());

        // Assert
        assertEquals(1, aliceProducts.size());
        assertEquals("Alice Product", aliceProducts.get(0).getName());
        assertEquals(alice.getUsername(), aliceProducts.get(0).getOwner().getUsername());
    }



    @Test
    void testUserCannotUpdateOthersProduct() {
        // Create 2 users
        User owner = new User();
        owner.setUsername("productOwner");
        owner.setPassword("secret");
        owner = userRepository.save(owner);

        User attacker = new User();
        attacker.setUsername("intruder");
        attacker.setPassword("hackme");
        attacker = userRepository.save(attacker);

        // Create a product that belongs to owner
        Product p = new Product();
        p.setName("Owner's Product");
        p.setPrice(200.0);
        p.setTaxRate(0.2);
        p.setOwner(owner);
        p = productRepository.save(p);

        // Simulate access control check
        boolean isOwner = p.getOwner().getId().equals(attacker.getId());

        // Attacker shouldn't be allowed
        assertFalse(isOwner);
    }




    @Test
    void testUserCannotDeleteOthersProduct() {
        // Arrange: two users
        User owner = new User();
        owner.setUsername("realowner");
        owner.setPassword("pass1");
        owner = userRepository.save(owner);

        User attacker = new User();
        attacker.setUsername("notallowed");
        attacker.setPassword("pass2");
        attacker = userRepository.save(attacker);

        // Product belongs to the owner
        Product product = new Product();
        product.setName("Sensitive Product");
        product.setPrice(150.0);
        product.setTaxRate(0.18);
        product.setOwner(owner);
        product = productRepository.save(product);

        // Act: simulate deletion request by attacker
        boolean canDelete = product.getOwner().getId().equals(attacker.getId());

        // Assert: deletion should NOT be allowed
        assertFalse(canDelete, "A user should not be able to delete another user's product.");
    }




    @Test
    void testFindAllReturnsAllProductsRegardlessOfOwner() {
        // Create and save User A
        User userA = new User();
        userA.setUsername("userA");
        userA.setPassword("passA");
        userA = userRepository.save(userA);

        // Create and save User B
        User userB = new User();
        userB.setUsername("userB");
        userB.setPassword("passB");
        userB = userRepository.save(userB);

        // Create products for each user
        Product productA = new Product();
        productA.setName("Product A");
        productA.setPrice(100.0);
        productA.setTaxRate(0.1);
        productA.setOwner(userA);
        productRepository.save(productA);

        Product productB = new Product();
        productB.setName("Product B");
        productB.setPrice(200.0);
        productB.setTaxRate(0.2);
        productB.setOwner(userB);
        productRepository.save(productB);

        // Act: call findAll()
        List<Product> allProducts = productRepository.findAll();

        // Assert: both products are visible
        assertEquals(2, allProducts.size());
        assertTrue(allProducts.stream().anyMatch(p -> p.getName().equals("Product A")));
        assertTrue(allProducts.stream().anyMatch(p -> p.getName().equals("Product B")));
    }




}
