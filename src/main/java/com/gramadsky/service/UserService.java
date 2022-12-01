package com.gramadsky.service;

import com.gramadsky.model.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Integer id);

    void deleteUser(User user);

    void saveUser(User user);

    void updateUser(User user);

}
