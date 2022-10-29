package com.gramadsky.controller;

import com.gramadsky.model.entity.Login;
import com.gramadsky.model.entity.User;
import com.gramadsky.security.services.RegistrationService;
import com.gramadsky.security.util.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final LoginValidator loginValidator;

    @Autowired
    public AuthController(RegistrationService registrationService, LoginValidator loginValidator) {
        this.registrationService = registrationService;
        this.loginValidator = loginValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("login") Login login,
                                   @ModelAttribute("user") User user)
    {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("login") @Valid Login login,
                                      @ModelAttribute("user") User user,
                                      BindingResult bindingResult) {
        loginValidator.validate(login, bindingResult);
        if (bindingResult.hasErrors())
            return "/auth/registration";
        registrationService.register(login, user);
        return "redirect:/auth/login";
    }
}

