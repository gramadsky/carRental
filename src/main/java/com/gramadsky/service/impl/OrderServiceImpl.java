package com.gramadsky.service.impl;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.Discount;
import com.gramadsky.model.entity.Order;
import com.gramadsky.model.repository.OrderRepository;
import com.gramadsky.security.services.RegistrationService;
import com.gramadsky.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final DiscountServiceImpl discountService;
    private final OrderRepository repository;
//    private final CarServiceImpl carService;
//    private final OrderServiceImpl orderService;
//    private final RegistrationService registrationService;

    @Override
    public List<Order> findAll() {return repository.findAll();}

    @Override
    public Order findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(Order order) {repository.save(order);}

    @Override
    public void deleteOrder(Integer id) {
        repository.deleteById(id);
    }

    public List<Order> findByUserId(Integer id){
        return repository.findOrderByUserId(id);
    }

    @Override
    public Order findByLastId() {
        return repository.findFirstByOrderByIdDesc();
    }

    public void calculationRent(Order order, Car car){
        int days = Math.toIntExact((order.getEndOfRental().toEpochDay() - order.getStartOfRental().toEpochDay()));
        Discount discount = discountService.findById(days);
        order.setReduction(car.getCost() * days * ((float)discount.getPercent() / 100));
        order.setPrice(car.getCost() * days - order.getReduction());
    }

//    public void saveNewOrder(Integer id, Order order){
//        Car car = carService.findById(id);
//        car.setAvailability("busy until " + order.getEndOfRental());
//        carService.updateCar(car);
//        order.setCar(car);
//        order.setUser(registrationService.findRegisteredUser());
//        order.setPaid("not paid");
//        orderService.calculationRent(order, car);
//        orderService.save(order);

//    }
}

