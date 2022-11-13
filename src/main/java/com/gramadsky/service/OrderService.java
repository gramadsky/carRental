package com.gramadsky.service;

import com.gramadsky.model.entity.Order;
import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(Integer id);

    void save(Order order);

    void deleteOrder(Integer id);

    List<Order> findByUserId(Integer id);

    Order findByLastId();
}
