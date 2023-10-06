package org.binaracademy.challenge_4.service;

import org.binaracademy.challenge_4.entity.Admin;
import org.binaracademy.challenge_4.entity.Merchant;
import org.binaracademy.challenge_4.entity.User;

import java.util.List;

public interface AdminService {
    boolean createAdmin(User user, String name, String location);
    List<Merchant> getAllMerchant(User user);

    boolean checkIsAdmin(User user);

    Admin getAdmin(User user);

    boolean updateAdmin(Admin admin);
}
