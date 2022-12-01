package com.gramadsky.security.services;

import com.gramadsky.model.entity.Login;
import com.gramadsky.model.entity.User;
import com.gramadsky.security.LoginDetails;
import com.gramadsky.security.repositories.PeopleRepository;
import com.gramadsky.security.repositories.UsersRepository;
import com.gramadsky.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Log4j2
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final UserServiceImpl userService;
    private final UsersRepository usersRepository;

    public String register(Login login, User user, String password, String confirmPassword, Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match");
            return "/auth/registration";
        }
        login.setRole(Login.Role.ROLE_CLIENT);
        login.setPassword(password);
        user.setStatus(User.Status.NO_DEBTS);
        login.setUser(user);
        user.setLogin(login);

        peopleRepository.save(login);
        usersRepository.save(user);
        log.info("New user: " + user.getName() + " " + user.getSurname() + " has been created");

        return "redirect:/auth/login";
    }

    public User findRegisteredUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDetails loginDetails = (LoginDetails) authentication.getPrincipal();
        return userService.findById(loginDetails.getUserId());
    }

    public Login.Role findLoginRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDetails loginDetails = (LoginDetails) authentication.getPrincipal();
        return loginDetails.getLogin().getRole();
    }

    public String goToMyProfile(Model model) {
        model.addAttribute("user", findRegisteredUser());
        return "user/myProfile";
    }

    public String changeProfile(String email, String passportData, Model model) {
        User user = findRegisteredUser();
        user.setEmail(email);
        user.setPassportData(passportData);
        usersRepository.save(user);
        model.addAttribute("user", user);
        model.addAttribute("message1", "Personal data was changed");
        log.info(user.getName() + " " + user.getSurname() + " changed your passport details");
        return "user/myProfile";
    }

    public String changePassword(String oldPassword, String newPassword, String confirmPassword, Model model) {
        User user = findRegisteredUser();
        model.addAttribute("user", user);
        if (!user.getLogin().getPassword().equals(oldPassword))
            model.addAttribute("errorMessage", "Invalid password");
        else if (!newPassword.equals(confirmPassword))
            model.addAttribute("errorMessage2", "Passwords do not match");
        else {
            user.getLogin().setPassword(newPassword);
            usersRepository.save(user);
            model.addAttribute("message", "Password was changed");
            log.info(user.getName() + " " + user.getSurname() + " changed your password");
        }
        return "user/myProfile";
    }
}
