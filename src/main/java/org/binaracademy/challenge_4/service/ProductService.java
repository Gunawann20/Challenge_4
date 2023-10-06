package org.binaracademy.challenge_4.service;

import org.binaracademy.challenge_4.entity.Product;

public interface ProductService {

    Product getProductById(Long id);
    boolean updateName(Long id, String name);
    boolean updatePrice(Long id, Integer price);
    boolean deleteProduct(Long id);
}
