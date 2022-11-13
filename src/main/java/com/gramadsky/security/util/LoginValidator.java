package com.gramadsky.security.util;


import com.gramadsky.model.entity.Login;
import com.gramadsky.security.services.LoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoginValidator implements Validator {

    private final LoginDetailsService loginDetailsService;

    @Autowired
    public LoginValidator(LoginDetailsService loginDetailsService) {
        this.loginDetailsService = loginDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Login.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Login login = (Login) o;

        try {
            loginDetailsService.loadUserByUsername(login.getUsername());
        }catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("login.username","", "This username is already taken");
    }
}
