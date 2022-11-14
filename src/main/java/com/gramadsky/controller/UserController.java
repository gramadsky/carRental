package com.gramadsky.controller;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.Order;
import com.gramadsky.model.entity.User;
import com.gramadsky.security.repositories.UsersRepository;
import com.gramadsky.security.services.RegistrationService;
import com.gramadsky.service.impl.CarServiceImpl;
import com.gramadsky.service.impl.OrderServiceImpl;
import com.gramadsky.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final CarServiceImpl carService;
    private final OrderServiceImpl orderService;
    private final RegistrationService registrationService;
    private final UserServiceImpl userRepository;
//    private final UsersRepository usersRepository;

    @GetMapping("/hello")
    public String cars(Model model) {
        List<Car> cars = carService.findAll();
        String name = registrationService.findRegisteredUser().getName();
        model.addAttribute("cars", cars);
        model.addAttribute("name", name);

        if (registrationService.findLoginRole().equals("ROLE_ADMIN")) {
            return "admin/car-list";
        }
        return "user/car-list";
    }

    @GetMapping("/chooseCar/{id}")
    public String chooseCar(@PathVariable("id") Integer id,
                            @ModelAttribute("order") Order order,
                            Model model) {
        Car car = carService.findById(id);
        if (!car.getAvailability().equals("available")) {
            model.addAttribute("errorMessage", "This car is busy. Please choose another car.");
            List<Car> cars = carService.findAll();
            model.addAttribute("cars", cars);
            return "user/car-list";
        } else {
            User user = registrationService.findRegisteredUser();
            model.addAttribute("car", car);
            model.addAttribute("user", user);
            return "user/chooseCar";
        }
    }

    @PostMapping("/chooseCar/{id}")
    public String newOrder(@PathVariable("id") Integer id, @ModelAttribute("order") Order order, Model model) {
        Car car = carService.findById(id);
        model.addAttribute("car", car);

        userRepository.updateUser(order,model);
//        User user = registrationService.findRegisteredUser();
//        user.setPassportData(order.getUser().getPassportData());
//        model.addAttribute("user", user);
//        usersRepository.save(user);

        car.setAvailability("busy until " + order.getEndOfRental());
        carService.updateCar(car);
        order.setCar(car);
        order.setUser(registrationService.findRegisteredUser());
        order.setStatus(Order.Status.WAITING_PAYMENT);
        orderService.calculationRent(order, car);
        orderService.save(order);

        return "user/payOrder";
    }

    @PostMapping("chooseCar/order/{id}")
    public String saveOrder(@RequestParam("action") String name,
                            @PathVariable("id") Integer id, Model model) {
        Order order = orderService.findByLastId();
        if (name.equals("payNow")) {
            order.setStatus(Order.Status.PAID_WAITING_CONFIRMATION);
            model.addAttribute("order", order);
            orderService.save(order);
            return "redirect:/hello";
        } else {
            Integer idUser = registrationService.findRegisteredUser().getId();
            List<Order> orders = orderService.findByUserId(idUser);
            model.addAttribute("orders", orders);
            return "user/orders";
        }
    }

    @GetMapping("/orders")
    public String myOrders(Model model) {
        Integer id = registrationService.findRegisteredUser().getId();
        List<Order> orders = orderService.findByUserId(id);
        model.addAttribute("orders", orders);
        return "user/orders";
    }

    @PostMapping("/pay/{id}")
    public String pay(@PathVariable("id") Integer id,
                      Model model) {
        Order order = orderService.findById(id);
        order.setStatus(Order.Status.PAID_WAITING_CONFIRMATION);
        model.addAttribute("order", order);
//        orderService.save(order);
        return "user/payOrder";
    }

    @PostMapping("pay/order/{id}")
    public String payOrder(@RequestParam("action") String name,
                           @PathVariable("id") Integer id, Model model) {
        Order order = orderService.findById(id);
        Integer idUser = registrationService.findRegisteredUser().getId();
        List<Order> orders = orderService.findByUserId(idUser);
        model.addAttribute("orders", orders);
        if (name.equals("payNow")) {
            order.setStatus(Order.Status.PAID_WAITING_CONFIRMATION);
            model.addAttribute("order", order);
            orderService.save(order);
        }
        return "user/orders";

    }
}