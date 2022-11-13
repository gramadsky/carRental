package com.gramadsky.controller;

import com.gramadsky.model.entity.*;
import com.gramadsky.service.LoginService;
import com.gramadsky.service.impl.CarClassServiceImpl;
import com.gramadsky.service.impl.CarServiceImpl;
import com.gramadsky.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final OrderServiceImpl orderService;
    private final LoginService loginService;
    private final CarServiceImpl carService;
    private final CarClassServiceImpl carClassService;

    @GetMapping("/orders")
    public String orderList(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "admin/order-list";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<Login> logins = loginService.findAll();
        model.addAttribute("logins", logins);
        return "admin/user-list";
    }

    @PostMapping("/pending/{id}")
    public String pending(@RequestParam("action") String name,
                          @PathVariable("id") Integer id) {
        Order order = orderService.findById(id);
        if (name.equals("confirmed")) {
            order.setStatus(Order.Status.CONFIRMED);
        } else
            order.setStatus(Order.Status.DENIED);
        orderService.save(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("cars")
    public String cars(Model model) {
        List<Car> cars = carService.findAll();
        model.addAttribute("cars", cars);
        return "admin/car-list";
    }

    @GetMapping("/selectCar/{id}")
    public String chooseCar(@PathVariable("id") Integer id,
                            Model model) {
        Car car = carService.findById(id);
        model.addAttribute("car", car);
        return "admin/selectCar";
    }

    @PostMapping("/updateCar/{id}")
    public String updateCar(@PathVariable("id") Integer id,
                            @RequestParam("cost") Float cost,
                            @RequestParam("availability") String availability,
                            Model model) {
        Car car = carService.findById(id);
        car.setCost(cost);
        car.setAvailability(availability);
        model.addAttribute("car", car);
        carService.updateCar(car);
        return "admin/selectCar";
    }

    @GetMapping("/newCar")
    public String newCar(@ModelAttribute("car") Car car,
                         Model model) {
        List<CarClass> carClasses = carClassService.findAll();
        model.addAttribute("carClasses", carClasses);

        return "admin/newCar";
    }

    @PostMapping("/saveNewCar")
    public String saveNewCar(@RequestParam("nameClass")String nameClass, Car car) {
        carService.saveNewCar(nameClass, car);
        return "redirect:/admin/cars";
    }

    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") Integer id) {
//        carService.deleteCar(carService.findById(id));
        return "redirect:/hello";
    }
}
