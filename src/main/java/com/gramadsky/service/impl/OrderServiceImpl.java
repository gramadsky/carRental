package com.gramadsky.service.impl;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.Discount;
import com.gramadsky.model.entity.Order;
import com.gramadsky.model.entity.User;
import com.gramadsky.model.repository.OrderRepository;
import com.gramadsky.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final DiscountServiceImpl discountService;
    private final OrderRepository repository;

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Order findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void registrationOrder(Order order) {
        repository.save(order);

    }

//    @Override
//    public void registrationOrder(Order order, User user, Car car, Discount discount) {
//        order.setUser(user);
//        order.setCar(car);
//        Long days = order.getStartOfRental().toEpochDay() - order.getStartOfRental().toEpochDay();
//
//        discount.setAmountOfDays(days);
//
//        order.setPrice(car.getCost() * days * discountService.findById(days.intValue());
//        repository.save(order);
//    }

    @Override
    public void deleteOrder(Integer id) {
        repository.deleteById(id);
    }


}

