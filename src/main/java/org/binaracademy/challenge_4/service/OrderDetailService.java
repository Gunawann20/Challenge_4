package org.binaracademy.challenge_4.service;

import org.binaracademy.challenge_4.entity.Order;
import org.binaracademy.challenge_4.entity.Product;

public interface OrderDetailService {
    boolean addOrderDetail(Order order, Product product, Integer quantity);
}
