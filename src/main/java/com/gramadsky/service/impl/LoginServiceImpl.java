package com.gramadsky.service.impl;

import com.gramadsky.model.entity.Login;
import com.gramadsky.model.repository.LoginRepository;
import com.gramadsky.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final LoginRepository loginRepository;

    @Override
    public List<Login> findAll() {
        return loginRepository.findAll();
    }

    @Override
    public Login findById(Integer id) {
        return loginRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Login login) {
        loginRepository.save(login);
    }

    @Override
    public void delete(Login login) {
        loginRepository.delete(login);
    }
}
