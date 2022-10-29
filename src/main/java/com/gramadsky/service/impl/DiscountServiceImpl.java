package com.gramadsky.service.impl;

import com.gramadsky.model.entity.Discount;
import com.gramadsky.model.repository.DiscountRepository;
import com.gramadsky.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private DiscountRepository repository;

    @Override
    public Discount findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
