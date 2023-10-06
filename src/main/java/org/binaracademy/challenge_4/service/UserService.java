package org.binaracademy.challenge_4.service;

import org.binaracademy.challenge_4.entity.Product;
import org.binaracademy.challenge_4.entity.User;

public interface UserService {
    User login(String email, String password);
    boolean register(String username, String email, String password);

}
