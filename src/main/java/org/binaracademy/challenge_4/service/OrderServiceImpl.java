package org.binaracademy.challenge_4.service;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.challenge_4.entity.Order;
import org.binaracademy.challenge_4.entity.OrderDetail;
import org.binaracademy.challenge_4.entity.Product;
import org.binaracademy.challenge_4.entity.User;
import org.binaracademy.challenge_4.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public boolean addOrder(User user, Product product, String destination, Integer quantity) {
        Order order = new Order();
        order.setUser(user);
        order.setTime(LocalDateTime.now());
        order.setDestination(destination);
        order.setIsCompleted(false);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
        orderDetail.setTotalPrice(((long) product.getPrice() * quantity));
        order.setOrderDetails(orderDetail);

        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean updateOrder(Order order) {
        try {
            orderRepository.save(order);
            return true;
        }catch (Exception e){
            log.error("Terjadi exception : "+e.getMessage());
            return false;
        }
    }

    @Override
    public Page<Order> showOrderByUserIdIsCompleted(User user, int page) {
        try {
            Pageable pageable = PageRequest.of(page, 5);
            return orderRepository.findByUser_IdAndIsCompleted(user.getId(), true, pageable);
        }catch (Exception e){
            log.error("Terjadi exception : "+ e.getMessage());
            return null;
        }
    }

    @Override
    public Page<Order> showOrderByuserIdIsNotCompleted(User user, int page) {
        try {
            Pageable pageable = PageRequest.of(page, 5);
            return orderRepository.findByUser_IdAndIsCompleted(user.getId(), false, pageable);
        }catch (Exception e){
            log.error("Terjadi exception : "+ e.getMessage());
            return null;
        }
    }
}
