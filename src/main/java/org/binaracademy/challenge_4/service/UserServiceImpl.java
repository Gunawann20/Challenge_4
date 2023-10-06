package org.binaracademy.challenge_4.service;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.challenge_4.entity.Order;
import org.binaracademy.challenge_4.entity.OrderDetail;
import org.binaracademy.challenge_4.entity.Product;
import org.binaracademy.challenge_4.entity.User;
import org.binaracademy.challenge_4.repository.UserRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User login(String email, String password) {
        try {
            log.info("login");
            return userRepository.findUserByEmailAndPassword(email, password).orElse(null);
        }catch (Exception e){
            log.error("Terjadi exception");
            return null;
        }
    }

    @Override
    public boolean register(String username, String email, String password) {
        try {
            User user = new User(username, email, password);
            userRepository.save(user);
            log.info("Register berhasil");
            return true;
        }catch (Exception e){
            log.error("Terjadi exception");
            return false;
        }
    }
}
