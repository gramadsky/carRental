package com.gramadsky.service.impl;

import com.gramadsky.model.entity.User;
import com.gramadsky.model.repository.UserRepository;
import com.gramadsky.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(User user) {
        repository.delete(user);
    }

    @Override
    public void saveUser(User user) {
        repository.save(user);
    }

    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void updateUser(User user) {
        repository.save(user);
    }

    public void blocking(String name, Integer id) {
        User user = findById(id);
        if (name.equals("block")) {
            user.setStatus(User.Status.BLOCKED);
        } else
            user.setStatus(User.Status.NO_DEBTS);
        updateUser(user);
        log.info(user.getName() + " " + user.getSurname() + " status has been changed to " + user.getStatus());
    }
}


