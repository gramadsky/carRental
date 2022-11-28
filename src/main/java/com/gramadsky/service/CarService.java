package com.gramadsky.service;

import com.gramadsky.model.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarService {
    List<Car> findAll();

    Car findById(Integer id);

    void deleteCar(Car car);

    String selectCar(Integer id, Model model);

    void updateCar(Car car);

    void updateAvailabilityCar(Car car, Integer id);

    void saveNewCar(String nameClass, String status, Car.Transmission transmission, MultipartFile image, Car car) throws IOException;

    Page<Car> findPaginated(int pageNo, int pageSize);
}
