package com.gramadsky.service.impl;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.CarClass;
import com.gramadsky.model.repository.CarClassRepository;
import com.gramadsky.model.repository.CarRepository;
import com.gramadsky.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository repository;
    private final CarClassRepository carClassRepository;

    @Override
    public List<Car> findAll() {
        return repository.findAll();
    }

    @Override
    public Car findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteCar(Car car) {
        repository.delete(car);
    }

    @Override
    public void updateCar(Car car) {
        repository.save(car);
    }

    @Override
    public void saveNewCar(String nameClass, Car car) {
        CarClass carClass = carClassRepository.findByNameClass(nameClass);
        car.setCarClass(carClass);
        repository.save(car);
    }
}