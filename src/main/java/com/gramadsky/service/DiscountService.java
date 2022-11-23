package com.gramadsky.service;


import com.gramadsky.model.entity.Discount;

import java.util.List;

public interface DiscountService {

    Discount findById(Integer id);

    List<Discount> findAll();
}
