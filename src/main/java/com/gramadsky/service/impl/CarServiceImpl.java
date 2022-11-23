package com.gramadsky.service.impl;

import com.gramadsky.model.entity.*;
import com.gramadsky.model.repository.CarClassRepository;
import com.gramadsky.model.repository.CarRepository;
import com.gramadsky.model.repository.CarStatusRepository;
import com.gramadsky.model.repository.OrderRepository;
import com.gramadsky.security.services.RegistrationService;
import com.gramadsky.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final OrderRepository orderRepository;
    private final CarClassRepository carClassRepository;
    private final CarStatusServiceImpl carStatusService;
    private final CarStatusRepository carStatusRepository;
    private final RegistrationService registrationService;
    private final DiscountServiceImpl discountService;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findById(Integer id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCar(Car car) {
        carRepository.delete(car);
    }

    @Override
    public void updateCar(Car car) {
        carRepository.save(car);
    }

    public String carList(Model model) {
        User user = registrationService.findRegisteredUser();
        CarStatus carStatus = carStatusService.findById(3);
        model.addAttribute("cars", carRepository.findAllByCarStatusNotLike(carStatus));
        model.addAttribute("name", user.getName());
        log.info(user.getName() + " " + user.getSurname() + " is logged in at " + LocalDateTime.now());
        if (registrationService.findLoginRole() == Login.Role.ROLE_ADMIN) {
            return "redirect:/admin/orders";
        }
        return "user/car-list";
    }

    public void detailingInformation(Integer id, Model model) {
        Car car = findById(id);
        model.addAttribute("car", car);

        List<Integer> prices = discountService.getCarPricesPerDay(car);
        model.addAttribute("prices", prices);
    }

    @Override
    public String selectCar(Integer id, Model model) {
        Car car = findById(id);
        model.addAttribute("car", car);

        List<Order> carOrders = orderRepository.findByCarIdAndStatusNotLike(car.getId(), Order.Status.DENIED);
        model.addAttribute("carOrders", carOrders);

        User user = registrationService.findRegisteredUser();

        if (user.getStatus() == User.Status.INVOICE_NOT_PAID
                || user.getStatus() == User.Status.REPAIR_NOT_PAID) {
            model.addAttribute("errorMessage",
                    "You have unpaid bills! Please pay first.");
            List<Integer> prices = discountService.getCarPricesPerDay(car);
            model.addAttribute("prices", prices);
            return "user/detailedInformationCar";
        }

        model.addAttribute("user", user);
        return "user/chooseCar";
    }

    @Override
    public void updateAvailabilityCar(Car car, Integer id) {
        CarStatus carStatus = carStatusRepository.getReferenceById(id);
        car.setCarStatus(carStatus);
        updateCar(car);
    }

    @Override
    public void saveNewCar(String nameClass, String status,
                           Car.Transmission transmission, Car car) {
        car.setTransmission(transmission);
        car.setCarClass(carClassRepository.findByNameClass(nameClass));
        car.setCarStatus(carStatusRepository.findByCarStatus(status));
        carRepository.save(car);
    }

    @Override
    public Page<Car> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.carRepository.findAll(pageable);
    }

    public void sortCar(String nameClass, Model model) {
        List<Car> cars = carRepository.findByCarClassNameClass(nameClass);
        model.addAttribute("cars", cars);
    }
}