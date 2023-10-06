package org.binaracademy.challenge_4.service;

import org.binaracademy.challenge_4.entity.Merchant;
import org.springframework.data.domain.Page;

public interface MerchantService {
    Page<Merchant> getAllMerchant(int page);
    Merchant getDataMerchantById(Long id);
    boolean updateMerchantName(Merchant merchant);
    boolean deleteMerchantById(Merchant merchant);
    boolean updateMerchantOpen(Merchant merchant);
    boolean addProduct(Merchant merchant, String name, Integer price);
}
