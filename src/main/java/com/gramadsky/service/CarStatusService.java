package com.gramadsky.service;

import com.gramadsky.model.entity.CarStatus;

import java.util.List;

public interface CarStatusService {

    List<CarStatus> findAll();

    CarStatus findById(Integer id);
}
