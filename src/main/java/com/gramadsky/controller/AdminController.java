package com.gramadsky.controller;

import com.gramadsky.model.entity.*;
import com.gramadsky.model.repository.CarStatusRepository;
import com.gramadsky.service.LoginService;
import com.gramadsky.service.impl.*;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final OrderServiceImpl orderService;
    private final LoginService loginService;
    private final CarServiceImpl carService;
    private final CarClassServiceImpl carClassService;
    private final CarStatusServiceImpl carStatusService;
    private final CarStatusRepository carStatusRepository;
    private final UserServiceImpl userService;
    private final DegreeOfDamageServiceImpl degreeOfDamageService;

//    @GetMapping("/orders")
//    public String orderList(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
//                            Model model) {
//        Page<Order> pages = orderService.pageable(pageable);
//        model.addAttribute("pages", pages);
//        return "admin/order-list";
//    }


    @GetMapping("/orders/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 8;

        Page<Order> page = orderService.findPaginated(pageNo, pageSize);
        List<Order> listOrders = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOrders", listOrders);

        return "admin/order-list";
    }

    @GetMapping("/orders")
    public String viewHomePage(Model model) {
        return findPaginated(1, model);
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
        carService.updateAvailabilityCar(order.getCar(), 1);
        orderService.save(order);
        log.info("Status was changed in " + order);

        return "redirect:/admin/orders";
    }

    @GetMapping("cars/{pageNo}")
    public String cars(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 15;

        Page<Car> page = carService.findPaginated(pageNo, pageSize);
        List<Car> listCars = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listCars", listCars);

        return "admin/car-list";
    }

    @GetMapping("/cars")
    public String viewCarList(Model model) {
        return cars(1, model);
    }


    @GetMapping("/selectCar/{id}")
    public String chooseCar(@PathVariable("id") Integer id,
                            Model model) {
        List<CarStatus> carStatuses = carStatusService.findAll();
        model.addAttribute("carStatuses", carStatuses);
        Car car = carService.findById(id);
        model.addAttribute("car", car);
        model.addAttribute("orders", car.getOrders());

        return "admin/selectCar";
    }

    @PostMapping("/updateCar/{id}")
    public String updateCar(@PathVariable("id") Integer id,
                            @RequestParam("fuelConsumption") Float fuelConsumption,
                            @RequestParam("engineVolume") Float engineVolume,
                            @RequestParam("cost") Float cost,
                            @RequestParam("status") String status,
                            @RequestParam("totalCostCar") Integer totalCost) {
        Car car = carService.findById(id);
        car.setTotalCostCar(totalCost);
        car.setFuelConsumption(fuelConsumption);
        car.setEngineVolume(engineVolume);
        car.setCost(cost);
        CarStatus carStatus = carStatusRepository.findByCarStatus(status);
        car.setCarStatus(carStatus);
        carService.updateCar(car);
        log.info(car.getId() + ". " + car.getCarModel() + " has been updated");

        return "redirect:/admin/selectCar/{id}";
    }

    @GetMapping("/newCar")
    public String newCar(@ModelAttribute("car") Car car,
                         Model model) {
        List<CarClass> carClasses = carClassService.findAll();
        model.addAttribute("carClasses", carClasses);
        List<CarStatus> carStatuses = carStatusService.findAll();
        model.addAttribute("carStatuses", carStatuses);
        List<Car.Transmission> transmissions = Arrays.asList(Car.Transmission.values());
        model.addAttribute("transmissions", transmissions);

        return "admin/newCar";
    }

    @PostMapping("/saveNewCar")
    public String saveNewCar(@RequestParam("nameClass") String nameClass,
                             @RequestParam("status") String status,
                             @RequestParam("transmission") Car.Transmission transmission,
                             Car car) {
        carService.saveNewCar(nameClass, status, transmission, car);
        log.info("The new car are saved: " + car);

        return "redirect:/admin/cars";
    }

    @PostMapping("/checkedCar/{id}")
    public String checkedCar(@RequestParam("action") String name,
                             @PathVariable("id") Integer id) {
        Order order = orderService.findById(id);
        if (name.equals("completed")) {
            order.setStatus(Order.Status.COMPLETED);
            orderService.save(order);
            log.info(order + " status has been changed");
        } else {
            order.setStatus(Order.Status.CAR_DAMAGED);
            carService.updateAvailabilityCar(order.getCar(), 3);
            orderService.save(order);
            User user = order.getUser();
            user.setStatus(User.Status.REPAIR_NOT_PAID);
            userService.updateUser(user);
            log.info(order + " status has been changed. " + user + " status has been changed");
            return "redirect:/admin/calculateRepair/{id}";
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/calculateRepair/{id}")
    public String calculateRepair(@PathVariable("id") Integer id,
                                  Model model) {
        Order order = orderService.findById(id);
        List<DegreeOfDamage> damages = degreeOfDamageService.findAll();
        model.addAttribute("car", order.getCar());
        model.addAttribute("damages", damages);
        model.addAttribute("order", order);

        return "admin/calculateRepair";
    }

    @PostMapping("/calculateRepair/{id}")
    public String calculate(@PathVariable("id") Integer id,
                            @RequestParam("damage") Integer damage) {
        Order order = orderService.findById(id);
        Integer totalCost = order.getCar().getTotalCostCar();
        Integer damageCar = totalCost * damage / 100;
        order.setStatus(Order.Status.WAITING_FOR_PAYMENTS_FOR_REPAIR);
        order.setRepairBill(damageCar);
        orderService.save(order);
        log.info(order.getCar().getId() + ". " + order.getCar().getCarModel() +
                " was damaged.The repair bill was " + order.getRepairBill() + "$");

        return "redirect:/admin/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") Integer id) {
//        carService.deleteCar(carService.findById(id));
        return "redirect:/hello";
    }
}
