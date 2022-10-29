package com.gramadsky.controller;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.Order;
import com.gramadsky.model.entity.User;
import com.gramadsky.security.LoginDetails;
import com.gramadsky.security.services.LoginDetailsService;
import com.gramadsky.service.impl.CarServiceImpl;
import com.gramadsky.service.impl.DiscountServiceImpl;
import com.gramadsky.service.impl.OrderServiceImpl;
import com.gramadsky.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HelloController {

    private final CarServiceImpl carService;
    private final UserServiceImpl userService;
    private final OrderServiceImpl orderService;
    private final DiscountServiceImpl discountService;

    @GetMapping("/hello")
    public String cars(Model model) {

        List<Car> cars = carService.findAll();
        model.addAttribute("cars", cars);

        return "car-list";
    }

    @GetMapping("/chooseCar/{id}")
    public String chooseCar(@PathVariable("id") Integer id, Model model) {
        Car car = carService.findById(id);

        model.addAttribute("car", car);
        model.addAttribute("order", new Order());

        return "chooseCar";
    }

    @PostMapping("/chooseCar")
    public String newOrder(@PathVariable("id") Integer id,
                           @ModelAttribute ("order") Order order) {
        Car car = carService.findById(id);
        order.setCar(car);

        orderService.registrationOrder(order);
     return "";
    }

    @GetMapping("/show")
    public String show() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDetails loginDetails = (LoginDetails) authentication.getPrincipal();
        System.out.println(loginDetails.getLogin());
        return "hello";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
