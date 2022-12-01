package com.gramadsky.security.services;

import com.gramadsky.model.entity.Login;
import com.gramadsky.model.entity.User;
import com.gramadsky.security.repositories.PeopleRepository;
import com.gramadsky.security.repositories.UsersRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

@RunWith(SpringRunner.class)
@SpringBootTest
class RegistrationServiceTest {
    @Autowired
    private RegistrationService registrationService;
    @MockBean
    private PeopleRepository peopleRepository;
    @MockBean
    private UsersRepository usersRepository;

    Model model;

    @Test
    void registerTest() {
        User user = new User();
        Login login = new Login();
        String password = "12";
        String confirmPassword = "12";

        registrationService.register(login, user, password, confirmPassword, model);

        Assert.assertTrue(CoreMatchers.is(login.getRole()).matches(Login.Role.ROLE_CLIENT));
        Assert.assertTrue(CoreMatchers.is(user.getStatus()).matches(User.Status.NO_DEBTS));

        //проверка что userRepository был вызван один раз, и вызов происходил по методу save()
        Mockito.verify(usersRepository, Mockito.timeout(1)).save(user);
    }
}