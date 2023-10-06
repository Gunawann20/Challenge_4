package org.binaracademy.challenge_4.service;

import org.binaracademy.challenge_4.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Test
    void login() {
        User gunawan = userService.login("gun@email.com", "password");
        assertNotNull(gunawan);
        assertEquals("Gunawan", gunawan.getUsername());
        assertEquals("gun@email.com", gunawan.getEmail());
        assertEquals("password", gunawan.getPassword());
    }

    @Test
    void failedLoginWithWrongPassword(){
        User gunawan = userService.login("gun@email.com", "pass");
        assertNull(gunawan);
    }

    @Test
    void failedLoginWithWrongEmail(){
        User gunawan = userService.login("joko@email.com", "pass");
        assertNull(gunawan);
    }

    @Test
    void register() {
        User user = new User("Gunawan", "gun@email.com", "password");
        assertDoesNotThrow(()->{
            userService.register("Gunawan", "gun@email.com", "password");
        });
        User gunawan = userService.login("gun@email.com", "password");
        assertEquals(user.getUsername(), gunawan.getUsername());
        assertEquals(user.getEmail(), gunawan.getEmail());
        assertEquals(user.getPassword(), gunawan.getPassword());

    }
    @Test
    void registerIfAccountAlreadyExist() {
        assertDoesNotThrow(()->{
            userService.register("Gunawan", "gun@email.com", "password");
        });
        assertFalse(userService.register("Gunawan", "gun@email.com", "password"));
    }
}