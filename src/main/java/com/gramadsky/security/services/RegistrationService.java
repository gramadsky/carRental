package com.gramadsky.security.services;

import com.gramadsky.model.entity.Login;
import com.gramadsky.model.entity.User;
import com.gramadsky.security.repositories.PeopleRepository;
import com.gramadsky.security.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final UsersRepository usersRepository;


    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, UsersRepository usersRepository) {
        this.peopleRepository = peopleRepository;
        this.usersRepository = usersRepository;
    }

    public void register(Login login, User user) {
        login.setRole("ROLE_CLIENT");
        user.setStatus("no debts");
        login.setUser(user);
        user.setLogin(login);

        peopleRepository.save(login);
        usersRepository.save(user);

    }

}