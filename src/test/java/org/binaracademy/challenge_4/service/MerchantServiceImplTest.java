package org.binaracademy.challenge_4.service;

import org.binaracademy.challenge_4.entity.Admin;
import org.binaracademy.challenge_4.entity.Merchant;
import org.binaracademy.challenge_4.entity.Product;
import org.binaracademy.challenge_4.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MerchantServiceImplTest {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @Test
    void getAllMerchant() {
        Page<Merchant> merchants = merchantService.getAllMerchant(0);

        assertNotNull(merchants);
        assertEquals(0, merchants.getNumber());
        assertEquals(1, merchants.getTotalPages());
        assertEquals(1, merchants.getTotalElements());
    }

    @Test
    void getDataMerchantById() {
        Merchant merchant = merchantService.getDataMerchantById(1L);
        assertNotNull(merchant);
        assertEquals("Warung Nasi Cak Budi", merchant.getName());
    }

    @Test
    void updateMerchantName() {
    }

    @Test
    void deleteMerchantById() {
    }

    @Test
    void updateMerchantOpen() {
        User user = userService.login("gun@email.com", "password");
        boolean isAdmin = adminService.checkIsAdmin(user);
        assertTrue(isAdmin);
        List<Merchant> merchants = adminService.getAllMerchant(user);
        assertNotNull(merchants);

        Merchant merchant = merchants.get(0);
        merchant.setIsOpen(true);

        assertTrue(merchantService.updateMerchantOpen(merchant));
    }

    @Test
    void addProduct() {
        User user = userService.login("gun@email.com", "password");
        boolean isAdmin = adminService.checkIsAdmin(user);
        assertTrue(isAdmin);
        List<Merchant> merchants = adminService.getAllMerchant(user);
        assertNotNull(merchants);

        Merchant merchant = merchants.get(0);


        assertTrue(merchantService.addProduct(merchant, "Nasi Rames Cak Budi", 10000));
    }
}