package org.binaracademy.challenge_4.service;

import org.binaracademy.challenge_4.entity.Order;
import org.binaracademy.challenge_4.entity.Product;
import org.binaracademy.challenge_4.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    boolean addOrder(User user, Product product, String destination, Integer quantity);
    boolean updateOrder(Order order);

    Page<Order> showOrderByUserIdIsCompleted(User user, int page);
    Page<Order> showOrderByuserIdIsNotCompleted(User user, int page);
}
