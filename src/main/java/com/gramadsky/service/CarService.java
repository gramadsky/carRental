package com.gramadsky.service;

import com.gramadsky.model.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> findAll();

    Car findById(Integer id);

    void deleteCar(Integer id);

    void saveCar(Car car);

}
