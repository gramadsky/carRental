package com.gramadsky.service;

import com.gramadsky.model.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> findAll();

    Car findById(Integer id);

    void deleteCar(Car car);

    void updateCar(Car car);

    void saveNewCar(String nameClass,Car car);
}
