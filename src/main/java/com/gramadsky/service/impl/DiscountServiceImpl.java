package com.gramadsky.service.impl;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.Discount;
import com.gramadsky.model.repository.DiscountRepository;
import com.gramadsky.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository repository;

    @Override
    public Discount findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Discount> findAll() {
        return repository.findAll();
    }

    public List <Integer> getCarPricesPerDay(Car car){

        List<Discount> discounts = findAll();
        Integer oneThree = (int) car.getCost();
        Integer fourSeven = (int) (car.getCost() - (car.getCost() * discounts.get(4).getPercent() / 100));
        Integer eightFourteen = (int) (car.getCost() - (car.getCost() * discounts.get(8).getPercent() / 100));
        Integer fifteenThirty = (int) (car.getCost() - (car.getCost() * discounts.get(15).getPercent() / 100));
        Integer thirtyOneAndMore = (int) (car.getCost() - (car.getCost() * discounts.get(31).getPercent() / 100));

        List<Integer> prices = new ArrayList<>();
        prices.add(oneThree);
        prices.add(fourSeven);
        prices.add(eightFourteen);
        prices.add(fifteenThirty);
        prices.add(thirtyOneAndMore);

        return prices;
    }
}
