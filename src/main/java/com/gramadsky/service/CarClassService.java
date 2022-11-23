package com.gramadsky.service;

import com.gramadsky.model.entity.CarClass;

import java.util.List;

public interface CarClassService {

    List<CarClass> findAll();

    CarClass findById(Integer id);
}
