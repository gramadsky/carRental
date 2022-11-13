package com.gramadsky.service.impl;

import com.gramadsky.model.entity.CarClass;
import com.gramadsky.model.repository.CarClassRepository;
import com.gramadsky.service.CarClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarClassServiceImpl implements CarClassService {
    private final CarClassRepository repository;

    @Override
    public List<CarClass> findAll() {
        return repository.findAll();
    }

    @Override
    public CarClass findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
