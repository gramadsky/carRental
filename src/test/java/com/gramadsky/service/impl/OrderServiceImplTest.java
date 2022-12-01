package com.gramadsky.service.impl;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.Order;
import com.gramadsky.model.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;
    @MockBean
    private OrderRepository orderRepository;

    @Test
    void calculationRentTest() {
        Order order = new Order();
        order.setStartOfRental(LocalDate.of(2022, 12, 1));
        order.setEndOfRental(LocalDate.of(2022, 12, 2));
        Car car = new Car();
        car.setCost(100);
        orderService.calculationRent(order, car);

        assertAll(
                () -> assertEquals(0, order.getReduction()),
                () -> assertEquals(100, order.getPrice())
        );
    }
}