package com.gramadsky.security.services;

import com.gramadsky.model.entity.Login;
import com.gramadsky.security.LoginDetails;
import com.gramadsky.security.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public LoginDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Login> login = peopleRepository.findByUsername(s);

        if (login.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new LoginDetails(login.get());
    }
}
