package com.gramadsky.controller;

import com.gramadsky.model.entity.Login;
import com.gramadsky.model.entity.User;
import com.gramadsky.security.services.RegistrationService;
import com.gramadsky.security.util.LoginValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;
    private final LoginValidator loginValidator;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("login") Login login,
                                   @ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("login") @Valid Login login,
                                      @ModelAttribute("user") @Valid User user,
                                      @RequestParam("password") String password,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      Model model, BindingResult bindingResult) {
        loginValidator.validate(login, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "This username is already taken");
            return "/auth/registration";
        }
        return registrationService.register(login, user, password, confirmPassword, model);
    }
}

