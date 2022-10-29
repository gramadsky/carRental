package com.gramadsky.controller;


import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.Discount;
import com.gramadsky.model.entity.Order;
import com.gramadsky.model.entity.User;
import com.gramadsky.service.impl.CarServiceImpl;
import com.gramadsky.service.impl.DiscountServiceImpl;
import com.gramadsky.service.impl.OrderServiceImpl;
import com.gramadsky.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class OrderController {

    private final OrderServiceImpl orderService;
    private final CarServiceImpl carService;
    private final UserServiceImpl userService;
    private final DiscountServiceImpl discountService;

    public OrderController(OrderServiceImpl orderService, CarServiceImpl carService, UserServiceImpl userService, DiscountServiceImpl discountService) {
        this.orderService = orderService;
        this.carService = carService;
        this.userService = userService;
        this.discountService = discountService;
    }

    @GetMapping("/neworder")
    public String order(Model model) {

        model.addAttribute("order", new Order());

        return "orders";
    }

    @PostMapping("/order")
    public String orders(@ModelAttribute("order") Order order) {
        Car car = carService.findById(2);
        User user = userService.findById(1);
        order.setUser(user);
        order.setCar(car);
        orderService.registrationOrder(order);
        return "orders";
    }

}
