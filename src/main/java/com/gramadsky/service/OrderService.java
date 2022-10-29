package com.gramadsky.service;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.Discount;
import com.gramadsky.model.entity.Order;
import com.gramadsky.model.entity.User;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(Integer id);

    void registrationOrder(Order order);

    void deleteOrder(Integer id);

}
