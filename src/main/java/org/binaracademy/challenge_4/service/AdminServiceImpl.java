package org.binaracademy.challenge_4.service;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.challenge_4.entity.Admin;
import org.binaracademy.challenge_4.entity.Merchant;
import org.binaracademy.challenge_4.entity.User;
import org.binaracademy.challenge_4.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepository;
    @Override
    public boolean createAdmin(User user, String name, String location) {
        try {
            log.info("membuat data admin");
            Admin admin = new Admin();
            admin.setUser(user);
            admin.setMerchants(new ArrayList<>());

            Merchant merchant = new Merchant();
            merchant.setAdmin(admin);
            merchant.setName(name);
            merchant.setIsOpen(false);
            merchant.setLocation(location);
            merchant.setProducts(new ArrayList<>());

            admin.getMerchants().add(merchant);
            adminRepository.save(admin);
            return true;
        }catch (Exception e){
            log.error("Terjadi exception : "+e.getMessage());
            return false;
        }
    }

    @Override
    public List<Merchant> getAllMerchant(User user) {
        try{
            Admin admin = adminRepository.findAdminByUserId(user.getId()).orElse(null);
            if (admin != null){
                if (admin.getMerchants().isEmpty()){
                    log.info("Tidak punya merchant");
                    return null;
                }else {
                    log.info("ada merchant");
                    return admin.getMerchants();
                }
            }else {
                log.info("Bukan admin");
                return null;
            }
        }catch (Exception e){
            log.info("terjadi exception :"+ e.getMessage());
            return null;
        }
    }

    @Override
    public boolean checkIsAdmin(User user) {
        try {
            Admin admin = adminRepository.findAdminByUserId(user.getId()).orElse(null);
            if (admin == null){
                log.info("Bukan admin");
                return false;
            }else {
                log.info("Admin");
                return true;
            }
        }catch (Exception e){
            log.error("terjadi exception : "+ e.getMessage());
            return false;
        }
    }

    @Override
    public Admin getAdmin(User user) {
        return adminRepository.findAdminByUserId(user.getId()).orElse(null);
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        try {
            adminRepository.save(admin);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
