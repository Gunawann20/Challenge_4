package org.binaracademy.challenge_4.service;

import org.binaracademy.challenge_4.entity.Merchant;
import org.binaracademy.challenge_4.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @Test
    void createAdmin() {
        User user = userService.login("gun@email.com", "password");
        assertNotNull(user);

        assertTrue(adminService.createAdmin(user, "Warung Nasi Cak Budi", "Gunung Pati"));
    }

    @Test
    void getAllMerchant() {
        User user = userService.login("gun@email.com", "password");
        List<Merchant> merchants = adminService.getAllMerchant(user);
        assertNotNull(merchants);
        assertEquals(1, merchants.size());
        assertEquals("Warung Nasi Cak Budi", merchants.get(0).getName());
    }

    @Test
    void checkIsAdmin() {
        User user = userService.login("gun@email.com", "password");
        assertTrue(adminService.checkIsAdmin(user));
    }
}