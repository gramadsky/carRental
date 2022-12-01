package com.gramadsky.controller;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.Login;
import com.gramadsky.service.LoginService;
import com.gramadsky.service.impl.CarServiceImpl;
import com.gramadsky.service.impl.OrderServiceImpl;
import com.gramadsky.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final OrderServiceImpl orderService;
    private final LoginService loginService;
    private final CarServiceImpl carService;
    private final UserServiceImpl userService;

    @GetMapping("/orders/{pageNo}")
    public String findPaginatedOrders(@PathVariable(value = "pageNo") int pageNo, Model model) {
        orderService.findPaginatedOrders(pageNo, model);

        return "admin/order-list";
    }

    @GetMapping("/orders")
    public String viewHomePage(Model model) {
        return findPaginatedOrders(1, model);
    }

    @PostMapping("/pending/{id}")
    public String pending(@RequestParam("action") String name,
                          @PathVariable("id") Integer id) {
        orderService.pending(name, id);

        return "redirect:/admin/orders";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<Login> logins = loginService.findAll();
        model.addAttribute("logins", logins);

        return "admin/user-list";
    }

    @PostMapping("/blocking/{id}")
    public String blocking(@RequestParam("action") String name,
                           @PathVariable("id") Integer id) {
        userService.blocking(name, id);

        return "redirect:/admin/users";
    }

    @GetMapping("cars/{pageNo}")
    public String cars(@PathVariable(value = "pageNo") int pageNo, Model model) {
        carService.findPaginatedCars(pageNo, model);

        return "admin/car-list";
    }

    @GetMapping("/cars")
    public String viewCarList(Model model) {
        return cars(1, model);
    }

    @GetMapping("/selectCar/{id}")
    public String chooseCar(@PathVariable("id") Integer id,
                            Model model) {
        carService.chooseCar(id, model);

        return "admin/selectCar";
    }

    @PostMapping("/updateCar/{id}")
    public String updateCar(@PathVariable("id") Integer id,
                            @RequestParam("fuelConsumption") Float fuelConsumption,
                            @RequestParam("engineVolume") Float engineVolume,
                            @RequestParam("cost") Float cost,
                            @RequestParam("status") String status,
                            @RequestParam("totalCostCar") Integer totalCost,
                            @RequestParam("file") MultipartFile file) throws IOException {
        carService.updateCar(id, fuelConsumption, engineVolume, cost, status, totalCost, file);

        return "redirect:/admin/selectCar/{id}";
    }

    @GetMapping("/newCar")
    public String newCar(@ModelAttribute("car") Car car,
                         Model model) {
        carService.newCar(model);

        return "admin/newCar";
    }

    @PostMapping("/saveNewCar")
    public String saveNewCar(@RequestParam("nameClass") String nameClass,
                             @RequestParam("status") String status,
                             @RequestParam("transmission") Car.Transmission transmission,
                             @RequestParam("file") MultipartFile file,
                             Car car) throws IOException {
        carService.saveNewCar(nameClass, status, transmission, file, car);

        return "redirect:/admin/cars";
    }

    @PostMapping("/checkedCar/{id}")
    public String checkedCar(@RequestParam("action") String name,
                             @PathVariable("id") Integer id) {

        return orderService.checkedCar(name, id);
    }

    @GetMapping("/calculateRepair/{id}")
    public String calculateRepair(@PathVariable("id") Integer id,
                                  Model model) {
        orderService.calculateRepair(id, model);
        return "admin/calculateRepair";
    }

    @PostMapping("/calculateRepair/{id}")
    public String calculate(@PathVariable("id") Integer id,
                            @RequestParam("damage") Integer damage) {
        orderService.saveCalculateRepair(id, damage);

        return "redirect:/admin/orders";
    }
}
