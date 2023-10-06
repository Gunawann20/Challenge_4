package org.binaracademy.challenge_4.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    void updateName() {
        assertTrue(productService.updateName(1L, "Nasi Rames cak Budi"));
    }

    @Test
    void updatePrice() {
        assertTrue(productService.updatePrice(1L, 15000));
    }

    @Test
    void deleteProduct() {
    }
}