package com.gramadsky.model.repository;

import com.gramadsky.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findOrderByUserId(Integer id);

    Order findFirstByOrderByIdDesc();
}
