package com.gramadsky.model.repository;

import com.gramadsky.model.entity.Order;

import com.gramadsky.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByOrderByIdDesc();

    List<Order> findByCarIdAndStatusNotLike(Integer id, Order.Status status);

    Order findFirstByOrderByIdDesc();

    List<Order> findByUserOrderByIdDesc(User user);


    Page<Order> findAllByStatusNotLike(Order.Status status, Pageable pageable);

}
