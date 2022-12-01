package com.gramadsky.service.impl;

import com.gramadsky.model.entity.*;
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
    private final DegreeOfDamageServiceImpl degreeOfDamageService;

    @Override
    public List<Order> findAll() {
        return repository.findAll();
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
        List<Order> carOrders = repository.findByCarIdAndStatusNotLike(car.getId(), Order.Status.DENIED);

        List<Integer> prices = discountService.getCarPricesPerDay(car);
        model.addAttribute("prices", prices);

        model.addAttribute("carOrders", carOrders);
        model.addAttribute("user", user);
        model.addAttribute("car", car);

        if (user.getStatus() == User.Status.BLOCKED) {
            model.addAttribute("error",
                    "Your account has been blocked. Contact Support.");
            return "user/detailedInformationCar";
        }

        if (user.getStatus() == User.Status.INVOICE_NOT_PAID
                || user.getStatus() == User.Status.REPAIR_NOT_PAID) {
            model.addAttribute("errorMessage",
                    "You have unpaid bills! Please pay first.");
            return "user/detailedInformationCar";
        }

        if (user.getPassportData() == null) {
            if (passportData.isEmpty()) {
                model.addAttribute("errorMessage1",
                        "" +
                                "Passport details must not be empty!");
                return "user/detailedInformationCar";
            } else {
                user.setPassportData(passportData);
            }
        }

        if (order.getStartOfRental() == null) {
            model.addAttribute("errorMessage2", "Invalid start date of rental!");
            return "user/detailedInformationCar";
        }

        if (order.getStartOfRental().toEpochDay() < LocalDate.now().toEpochDay()) {
            model.addAttribute("errorMessage3",
                    "Invalid start date of rental!" +
                            " The rental start date cannot be earlier than the current date.");
            return "user/detailedInformationCar";
        }

        if (order.getEndOfRental() == null) {
            model.addAttribute("errorMessage4",
                    "Invalid end date of rental!");
            return "user/detailedInformationCar";
        }

        if (order.getStartOfRental().toEpochDay() >= order.getEndOfRental().toEpochDay()) {
            model.addAttribute("errorMessage5",
                    "Invalid end date of rental! " +
                            "The end date of the rental cannot be earlier than the start date of the rental.");
            return "user/detailedInformationCar";
        }

        for (int i = 0; i < carOrders.size(); i++) {
            if (carOrders.get(i).getStartOfRental().toEpochDay() <= order.getEndOfRental().toEpochDay() &&
                    order.getStartOfRental().toEpochDay() <= carOrders.get(i).getEndOfRental().toEpochDay()) {
                model.addAttribute("errorMessage6",
                        "At this time the car is not available!");
                return "user/detailedInformationCar";
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

    public void changeOrderStatusToCarReturn(Integer id) {
        Order order = findById(id);
        order.setStatus(Order.Status.CAR_RETURN);
        save(order);
        log.info(order + " status has been updated");
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
        log.info(user.getName() + " " + user.getSurname() + " paid for the repair. " +
                "Status has been updated to a " + user.getStatus() + "\n" +
                "order status " + order + " has been updated ");

        return "redirect:/orders";
    }

    public void findPaginatedOrders(int pageNo, Model model) {
        int pageSize = 8;

        Page<Order> page = findPaginated(pageNo, pageSize);
        List<Order> listOrders = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOrders", listOrders);
    }

    public void pending(String name, Integer id) {
        Order order = findById(id);
        if (name.equals("confirmed")) {
            order.setStatus(Order.Status.CONFIRMED);
        } else
            order.setStatus(Order.Status.DENIED);
        carService.updateAvailabilityCar(order.getCar(), 1);
        save(order);
        log.info("Status was changed in " + order);
    }

    public void calculateRepair(Integer id, Model model) {
        Order order = findById(id);
        List<DegreeOfDamage> damages = degreeOfDamageService.findAll();
        model.addAttribute("car", order.getCar());
        model.addAttribute("damages", damages);
        model.addAttribute("order", order);
    }

    public void saveCalculateRepair(Integer id, Integer damage) {
        Order order = findById(id);
        Integer totalCost = order.getCar().getTotalCostCar();
        Integer damageCar = totalCost * damage / 100;
        order.setStatus(Order.Status.WAITING_FOR_PAYMENTS_FOR_REPAIR);
        order.setRepairBill(damageCar);
        save(order);
        log.info(order.getCar().getId() + ". " + order.getCar().getCarModel() +
                " was damaged.The repair bill was " + order.getRepairBill() + "$");
    }

    public String checkedCar(String name, Integer id) {
        Order order = findById(id);
        if (name.equals("completed")) {
            order.setStatus(Order.Status.COMPLETED);
            save(order);
            log.info(order + " status has been changed");
        } else {
            order.setStatus(Order.Status.CAR_DAMAGED);
            carService.updateAvailabilityCar(order.getCar(), 3);
            save(order);
            User user = order.getUser();
            user.setStatus(User.Status.REPAIR_NOT_PAID);
            userService.updateUser(user);
            log.info(order + " status has been changed. " + user + " status has been changed");
            return "redirect:/admin/calculateRepair/{id}";
        }
        return "redirect:/admin/orders";
    }

    public void checkedOrder(Model model) {
        List<Order> orders = findAll();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getEndOfRental().toEpochDay() < LocalDate.now().toEpochDay() &&
                    orders.get(i).getStatus() == Order.Status.CONFIRMED) {
                orders.get(i).setStatus(Order.Status.EXPIRED);
                orders.get(i).getUser().setStatus(User.Status.BLOCKED);
                save(orders.get(i));
            }
        }
        User user = registrationService.findRegisteredUser();
        List<Order> orderList = repository.findByUserOrderByIdDesc(user);
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getStatus() == Order.Status.EXPIRED) {
                model.addAttribute("error",
                        "Your order has expired! Urgently need to turn the " + orderList.get(i).getCar().getCarModel());
            }
        }
    }
}

