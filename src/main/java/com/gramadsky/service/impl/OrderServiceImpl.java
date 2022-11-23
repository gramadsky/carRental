package com.gramadsky.service.impl;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.Discount;
import com.gramadsky.model.entity.Order;
import com.gramadsky.model.entity.User;
import com.gramadsky.model.repository.OrderRepository;
import com.gramadsky.security.services.RegistrationService;
import com.gramadsky.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Comparator;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final DiscountServiceImpl discountService;
    private final OrderRepository repository;
    private final UserServiceImpl userService;
    private final CarServiceImpl carService;
    private final RegistrationService registrationService;

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    public List<Order> findAllDesc() {
        return repository.findAllByOrderByIdDesc();
    }

    public Page pageable(Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Override
    public Order findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(Order order) {
        repository.save(order);
    }

    @Override
    public void deleteOrder(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Order findByLastId() {
        return repository.findFirstByOrderByIdDesc();
    }

    @Override
    public Page<Order> findPaginated(int pageNo, int pageSize) {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.repository.findAll(pageable);
    }

    public void calculationRent(Order order, Car car) {
        int days = Math.toIntExact((order.getEndOfRental().toEpochDay() - order.getStartOfRental().toEpochDay()));
        Discount discount = discountService.findById(days);
        order.setReduction(car.getCost() * days * ((float) discount.getPercent() / 100));
        order.setPrice(car.getCost() * days - order.getReduction());
    }

    public String creteNewOrder(Integer id, Order order, Model model, String passportData) {
        Car car = carService.findById(id);
        User user = registrationService.findRegisteredUser();
        List<Order> carOrders = car.getOrders();

        model.addAttribute("carOrders", carOrders);
        model.addAttribute("user", user);
        model.addAttribute("car", car);

        if (user.getPassportData() == null) {
            if (passportData.isEmpty()) {
                model.addAttribute("errorMessage1",
                        "" +
                                "Passport details must not be empty!");
                return "user/chooseCar";
            } else {
                user.setPassportData(passportData);
            }
        }

        if (order.getStartOfRental() == null) {
            model.addAttribute("errorMessage2","Invalid start date of rental!");
            return "user/chooseCar";
        }

        if (order.getStartOfRental().toEpochDay() < LocalDate.now().toEpochDay()) {
            model.addAttribute("errorMessage3",
                    "Invalid start date of rental! The rental start date cannot be earlier than the current date.");
            return "user/chooseCar";
        }

        if (order.getEndOfRental() == null) {
            model.addAttribute("errorMessage4",
                    "Invalid end date of rental!");
            return "user/chooseCar";
        }

        if (order.getStartOfRental().toEpochDay() >= order.getEndOfRental().toEpochDay()) {
            model.addAttribute("errorMessage5",
                    "Invalid end date of rental! The end date of the rental cannot be earlier than the start date of the rental.");
            return "user/chooseCar";
        }

        for (int i = 0; i < carOrders.size(); i++) {
            if (carOrders.get(i).getStartOfRental().toEpochDay() <= order.getEndOfRental().toEpochDay() &&
                    order.getStartOfRental().toEpochDay() <= carOrders.get(i).getEndOfRental().toEpochDay()) {
                model.addAttribute("errorMessage6",
                        "At this time the car is not available!");
                return "user/chooseCar";
            }
        }
        userService.updateUser(user);
        carService.updateAvailabilityCar(car, 2);
        order.setCreationTime(LocalDateTime.now());
        saveNewOrder(car, user, order);
        log.info("New " + order + " has been created");

        return "user/payOrder";
    }

    public void saveNewOrder(Car car, User user, Order order) {
        order.setCar(car);
        user.setStatus(User.Status.INVOICE_NOT_PAID);
        order.setUser(user);
        order.setStatus(Order.Status.WAITING_PAYMENT);
        calculationRent(order, car);
        save(order);
    }

    public String updateNewOrder(String name) {
        Order order = findByLastId();
        User user = registrationService.findRegisteredUser();
        if (name.equals("payNow")) {
            order.setStatus(Order.Status.PAID_WAITING_CONFIRMATION);
            user.setStatus(User.Status.NO_DEBTS);
            userService.updateUser(user);
            save(order);
            log.info(order + " has been updated ");
        }
        return "redirect:/orders";
    }

    public String myOrdersList(Model model) {
        User user = registrationService.findRegisteredUser();
        List<Order> orders = repository.findByUserOrderByIdDesc(user);

        model.addAttribute("orders", orders);
        return "user/orders";
    }

    public String selectUnpaidOrder(Integer id, Model model) {
        model.addAttribute("order", findById(id));
        return "user/payOrder";
    }

    public String payOrder(Integer id, String name) {
        Order order = findById(id);
        User user = registrationService.findRegisteredUser();
        if (name.equals("payNow")) {
            order.setStatus(Order.Status.PAID_WAITING_CONFIRMATION);
            user.setStatus(User.Status.NO_DEBTS);
            userService.updateUser(user);
            save(order);
            log.info(order + " has been updated");
        }
        return "redirect:/orders";
    }

    public String payRepair(Integer id) {
        Order order = findById(id);
        order.setStatus(Order.Status.COMPLETED);
        User user = registrationService.findRegisteredUser();
        user.setStatus(User.Status.NO_DEBTS);
        userService.updateUser(user);
        log.info(user.getName() + " " + user.getSurname() + " paid for the repair. Status has been updated to a " + user.getStatus() + "\n" +
                "order status " + order + " has been updated ");

        return "redirect:/orders";
    }
}

