package com.gramadsky.controller;

import com.gramadsky.model.entity.Order;
import com.gramadsky.security.services.RegistrationService;
import com.gramadsky.service.impl.CarServiceImpl;
import com.gramadsky.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequiredArgsConstructor
public class UserController {
    private final CarServiceImpl carService;
    private final OrderServiceImpl orderService;
    private final RegistrationService registrationService;

    @GetMapping("/hello")
    public String cars(Model model) {
        return carService.carList(model);
    }

    @GetMapping("/detailedInformation/{id}")
    public String choose(@PathVariable("id") Integer id,
                         Model model) {
        carService.detailingInformation(id, model);
        return "user/detailedInformationCar";
    }

    @GetMapping("/chooseCar/{id}")
    public String chooseCar(@PathVariable("id") Integer id,
                            @ModelAttribute("order") Order order,
                            Model model) {
        return carService.selectCar(id, model);
    }

    @PostMapping("/chooseCar/{id}")
    public String createNewOrder(@PathVariable("id") Integer id,
                                 @RequestParam(required = false) String passportData,
                                 @ModelAttribute("order") Order order, Model model) {
        return orderService.creteNewOrder(id, order, model, passportData);
    }

    @PostMapping("chooseCar/order/{id}")
    public String saveNewOrder(@RequestParam("action") String name) {
        return orderService.updateNewOrder(name);
    }

    @GetMapping("/orders")
    public String myOrders(Model model) {

        return orderService.myOrdersList(model);
    }

    @PostMapping("/pay/{id}")
    public String pay(@PathVariable("id") Integer id,
                      Model model) {
        return orderService.selectUnpaidOrder(id, model);
    }

    @PostMapping("pay/order/{id}")
    public String payOrder(@PathVariable("id") Integer id,
                           @RequestParam("action") String name) {
        return orderService.payOrder(id, name);
    }

    @GetMapping("/myProfile")
    public String myProfile(Model model) {
        return registrationService.goToMyProfile(model);
    }

    @PostMapping("/changeProfile")
    public String changeProfile(@RequestParam(value = "email", required = false) String email,
                                @RequestParam(value = "passportData", required = false) String passportData,
                                Model model) {
        return registrationService.changeProfile(email, passportData, model);
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam(value = "oldPassword", required = false) String oldPassword,
                                 @RequestParam(value = "newPassword", required = false) String newPassword,
                                 @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
                                 Model model) {
        return registrationService.changePassword(oldPassword, newPassword, confirmPassword, model);
    }

    @PostMapping("/returnCar/{id}")
    public String returnCar(@PathVariable("id") Integer id) {
        Order order = orderService.findById(id);
        order.setStatus(Order.Status.CAR_RETURN);
        orderService.save(order);
        log.info(order + " status has been updated");

        return "redirect:/orders";
    }

    @GetMapping("/payForRepairs/{id}")
    public String payForRepair(@PathVariable("id") Integer id,
                               Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "user/payRepairs";
    }

    @PostMapping("/payForRepairs/order/{id}")
    public String payForRepair(@PathVariable("id") Integer id) {
        return orderService.payRepair(id);
    }


    @GetMapping("/sortCar")
    public String sortCar(@RequestParam("class") String nameClass, Model model) {
        carService.sortCar(nameClass, model);
        return "user/car-list";
    }
}

