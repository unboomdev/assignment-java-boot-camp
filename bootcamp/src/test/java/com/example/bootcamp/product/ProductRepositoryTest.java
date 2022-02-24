package com.example.bootcamp.product;

import com.example.bootcamp.product.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByNameContainingIgnoreCase() {
        List<Product> result = productRepository.findByNameContainingIgnoreCase("nmd");

        assertEquals(3, result.size());
    }
}