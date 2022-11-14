package com.gramadsky.service;


import com.gramadsky.model.entity.Order;
import com.gramadsky.model.entity.User;
import org.springframework.ui.Model;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Integer id);

    void deleteCar(Integer id);

    void saveUser(User user);

    void updateUser(Order order, Model model);

}
