package com.gramadsky.service;

import com.gramadsky.model.entity.Login;

import java.util.List;

public interface LoginService {
    List<Login> findAll();

    Login findById(Integer id);

    void save(Login login);

    void deleteOrder(Integer id);

}
