package com.gramadsky.service;

import com.gramadsky.model.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;

public interface CarService {
    List<Car> findAll();

    Car findById(Integer id);

    void deleteCar(Car car);

    String selectCar(Integer id, Model model);

    void updateCar(Car car);

    void updateAvailabilityCar(Car car, Integer id);

    void saveNewCar(String nameClass,String status,Car.Transmission transmission, Car car);

    Page<Car> findPaginated(int pageNo, int pageSize);
}
