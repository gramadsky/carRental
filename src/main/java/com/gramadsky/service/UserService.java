package com.gramadsky.service;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Integer id);

    void deleteCar(Integer id);

    void saveUser(User user);

}
