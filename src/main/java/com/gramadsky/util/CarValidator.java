package com.gramadsky.util;

import com.gramadsky.model.entity.Car;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CarValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Car.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Car car = (Car) o;

        if(!car.getAvailability().equals("available")) {
            errors.rejectValue("car.availability","", "This car is not available");
        }
    }
}
