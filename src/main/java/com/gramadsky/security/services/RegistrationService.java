package com.gramadsky.security.services;

import com.gramadsky.model.entity.Login;
import com.gramadsky.model.entity.User;
import com.gramadsky.security.LoginDetails;
import com.gramadsky.security.repositories.PeopleRepository;
import com.gramadsky.security.repositories.UsersRepository;
import com.gramadsky.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final UserServiceImpl userService;
    private final UsersRepository usersRepository;

    public void register(Login login, User user) {
        login.setRole("ROLE_CLIENT");
        user.setStatus("no debts");
        login.setUser(user);
        user.setLogin(login);

        peopleRepository.save(login);
        usersRepository.save(user);
    }

    public User findRegisteredUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDetails loginDetails = (LoginDetails) authentication.getPrincipal();
        return userService.findById(loginDetails.getUserId());
    }

    public String findLoginRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDetails loginDetails = (LoginDetails) authentication.getPrincipal();
        return loginDetails.getLogin().getRole();
    }
}