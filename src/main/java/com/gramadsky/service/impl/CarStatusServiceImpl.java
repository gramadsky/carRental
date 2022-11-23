package com.gramadsky.service.impl;

import com.gramadsky.model.entity.CarStatus;
import com.gramadsky.model.repository.CarStatusRepository;
import com.gramadsky.service.CarStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarStatusServiceImpl implements CarStatusService {
    private final CarStatusRepository repository;

    @Override
    public List<CarStatus> findAll() {
        return repository.findAll();
    }

    @Override
    public CarStatus findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
