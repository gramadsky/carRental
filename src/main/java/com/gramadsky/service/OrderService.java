package com.gramadsky.service;

import com.gramadsky.model.entity.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(Integer id);

    void save(Order order);

    void deleteOrder(Integer id);

    Order findByLastId();

    Page<Order> findPaginated(int pageNo, int pageSize);
}
