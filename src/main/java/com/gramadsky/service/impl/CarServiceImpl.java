package com.gramadsky.service.impl;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.repository.CarRepository;
import com.gramadsky.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository repository;

    @Override
    public List<Car> findAll() {
        return repository.findAll();
    }

    @Override
    public Car findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteCar(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void saveCar(Car car) {
        repository.save(car);
    }
}
