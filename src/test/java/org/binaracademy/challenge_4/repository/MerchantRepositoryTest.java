package org.binaracademy.challenge_4.repository;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.challenge_4.entity.Merchant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MerchantRepositoryTest {

    @Autowired
    private MerchantRepository merchantRepository;

    @Test
    void findAllByIsOpen() {

    }

    @Test
    void deleteById() {
        merchantRepository.deleteById(2L);
    }
}