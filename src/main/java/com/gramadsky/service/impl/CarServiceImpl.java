package com.gramadsky.service.impl;

import com.gramadsky.constants.PageConst;
import com.gramadsky.model.entity.*;
import com.gramadsky.model.repository.*;
import com.gramadsky.security.services.RegistrationService;
import com.gramadsky.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
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
    private final CarClassServiceImpl carClassService;

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
        CarStatus carStatus = carStatusService.findById(1);
        List<Car> cars = carRepository.findAllByCarStatusLike(carStatus);

        model.addAttribute("cars", cars);
        model.addAttribute("name", user.getName());
        if (registrationService.findLoginRole() == Login.Role.ROLE_ADMIN) {
            return PageConst.REDIRECT_ADMIN_ORDERS;
        }
        return PageConst.USER_CARS;
    }

    public String detailingInformation(Integer id, Model model) {
        Car car = findById(id);
        model.addAttribute("car", car);

        List<Integer> prices = discountService.getCarPricesPerDay(car);
        model.addAttribute("prices", prices);

        List<Order> carOrders = orderRepository.findByCarIdAndStatusNotLike(car.getId(), Order.Status.DENIED);
        model.addAttribute("carOrders", carOrders);

        User user = registrationService.findRegisteredUser();
        model.addAttribute("user", user);

        if (user.getStatus() == User.Status.INVOICE_NOT_PAID
                || user.getStatus() == User.Status.REPAIR_NOT_PAID) {
            model.addAttribute("errorMessage",
                    "You have unpaid bills! Please pay first.");
        }
        if (user.getStatus() == User.Status.BLOCKED) {
            model.addAttribute("error",
                    "Your account has been blocked. Contact Support.");
        }

        return PageConst.USER_DETAILED_INFORMATION_CAR;
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
            return PageConst.USER_DETAILED_INFORMATION_CAR;
        }
        model.addAttribute("user", user);
        return PageConst.USER_CHOOSE_CAR;
    }

    public void updateAvailabilityCar(Car car, Integer id) {
        CarStatus carStatus = carStatusRepository.getReferenceById(id);
        car.setCarStatus(carStatus);
        updateCar(car);
    }

    public void saveNewCar(String nameClass, String status,
                           Car.Transmission transmission, MultipartFile file, Car car) throws IOException {
        car.setTransmission(transmission);
        car.setCarClass(carClassRepository.findByNameClass(nameClass));
        car.setCarStatus(carStatusRepository.findByCarStatus(status));

        if (file.getSize() != 0) {
            var base64EncodedImage = Base64.getMimeEncoder().encodeToString(file.getBytes());
            car.setImageCar(base64EncodedImage);
        }
        carRepository.save(car);
        log.info("The new car are saved: " + car);
    }

    public Page<Car> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.carRepository.findAll(pageable);
    }

    public void sortCarByNameClass(String nameClass, Model model) {
        List<Car> cars = carRepository.findByCarClassNameClass(nameClass);
        model.addAttribute("cars", cars);
    }

    public void findPaginatedCars(int pageNo, Model model) {
        int pageSize = 15;

        Page<Car> page = findPaginated(pageNo, pageSize);
        List<Car> listCars = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listCars", listCars);
    }

    public void chooseCar(Integer id, Model model) {
        List<CarStatus> carStatuses = carStatusService.findAll();
        model.addAttribute("carStatuses", carStatuses);
        Car car = findById(id);
        model.addAttribute("car", car);
        model.addAttribute("orders", car.getOrders());
    }

    public void updateCar(Integer id, Float fuelConsumption, Float engineVolume,
                          Float cost, String status, Integer totalCost, MultipartFile file) throws IOException {

        Car car = findById(id);
        car.setTotalCostCar(totalCost);
        car.setFuelConsumption(fuelConsumption);
        car.setEngineVolume(engineVolume);
        car.setCost(cost);
        CarStatus carStatus = carStatusRepository.findByCarStatus(status);
        car.setCarStatus(carStatus);

        if (file.getSize() != 0) {
            var base64EncodedImage = Base64.getMimeEncoder().encodeToString(file.getBytes());
            car.setImageCar(base64EncodedImage);
        }
        carRepository.save(car);
        log.info(car.getId() + ". " + car.getCarModel() + " has been updated");
    }

    public void newCar(Model model) {
        List<CarClass> carClasses = carClassService.findAll();
        model.addAttribute("carClasses", carClasses);
        List<CarStatus> carStatuses = carStatusService.findAll();
        model.addAttribute("carStatuses", carStatuses);
        List<Car.Transmission> transmissions = Arrays.asList(Car.Transmission.values());
        model.addAttribute("transmissions", transmissions);
    }
}