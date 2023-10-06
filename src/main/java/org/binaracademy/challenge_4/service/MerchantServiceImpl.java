package org.binaracademy.challenge_4.service;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.challenge_4.entity.Merchant;
import org.binaracademy.challenge_4.entity.Product;
import org.binaracademy.challenge_4.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService{

    @Autowired
    private MerchantRepository merchantRepository;
    @Override
    public Page<Merchant> getAllMerchant(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        try{
            log.info("Mengambil data merchant page:"+page);
            return merchantRepository.findAllByIsOpen(true, pageable);
        }catch (Exception e){
            log.error("Terjadi exception");
            return null;
        }
    }

    @Override
    public Merchant getDataMerchantById(Long id) {
        try {
            log.info("Mengambil data merchant by Id");
            return merchantRepository.findById(id).orElse(null);
        }catch (Exception e){
            log.error("Terjadi exception");
            return null;
        }
    }

    @Override
    public boolean updateMerchantName(Merchant merchant) {
        try {
            log.info("update data merchant");
            merchantRepository.save(merchant);
            return true;
        }catch (Exception e){
            log.error("Terjadi exception");
            return false;
        }
    }

    @Override
    public boolean deleteMerchantById(Merchant merchant) {
        try {
            merchantRepository.deleteById(merchant.getId());
            return true;
        }catch (Exception e){
            log.error("Terjadi exception");
            return false;
        }
    }

    @Override
    public boolean updateMerchantOpen(Merchant merchant) {
        try {
            log.info("update open merchant");
            merchantRepository.save(merchant);
            return true;
        }catch (Exception e){
            log.error("Terjadi exception");
            return false;
        }
    }

    @Override
    public boolean addProduct(Merchant merchant, String name, Integer price) {
        try {
            Merchant merchantUpdate = merchantRepository.findById(merchant.getId()).orElse(null);
            if (merchantUpdate == null){
                log.info("Merchant tidak ada");
                return false;
            }else {
                Product product = new Product();
                product.setMerchant(merchantUpdate);
                product.setName(name);
                product.setPrice(price);
                merchantUpdate.getProducts().add(product);
                merchantRepository.save(merchantUpdate);
                log.info("Berhasil menambahkan produk");
                return true;
            }
        }catch (Exception e){
            log.error("Terjadi exception :"+ e.getMessage());
            return false;
        }

    }
}
