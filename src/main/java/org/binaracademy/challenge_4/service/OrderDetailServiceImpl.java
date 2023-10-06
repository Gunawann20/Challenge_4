package org.binaracademy.challenge_4.service;

import org.binaracademy.challenge_4.entity.Order;
import org.binaracademy.challenge_4.entity.OrderDetail;
import org.binaracademy.challenge_4.entity.Product;
import org.binaracademy.challenge_4.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public boolean addOrderDetail(Order order, Product product, Integer quantity) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
        orderDetail.setTotalPrice(((long) quantity * product.getPrice()));

        orderDetailRepository.save(orderDetail);
        return true;
    }
}
