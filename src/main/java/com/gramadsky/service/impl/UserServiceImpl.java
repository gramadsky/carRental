package com.gramadsky.service.impl;


import com.gramadsky.model.entity.Login;
import com.gramadsky.model.entity.Order;
import com.gramadsky.model.entity.User;
import com.gramadsky.security.repositories.UsersRepository;
import com.gramadsky.security.services.RegistrationService;
import com.gramadsky.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepository repository;

    @Override

    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteCar(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    public void updateUser(User user) {
        repository.save(user);
    }
}