package com.gramadsky.service.impl;

import com.gramadsky.model.entity.DegreeOfDamage;
import com.gramadsky.model.repository.DegreeOfDamageRepository;
import com.gramadsky.service.DegreeOfDamageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DegreeOfDamageServiceImpl implements DegreeOfDamageService {
    private final DegreeOfDamageRepository repository;

    @Override
    public List<DegreeOfDamage> findAll() {
        return repository.findAll();
    }
}
