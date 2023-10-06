package org.binaracademy.challenge_4.service;

import org.binaracademy.challenge_4.entity.Order;
import org.binaracademy.challenge_4.entity.OrderDetail;
import org.binaracademy.challenge_4.entity.Product;
import org.binaracademy.challenge_4.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDetailServiceImplTest {

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;
    @Test
    void addOrderDetail() {


    }
}