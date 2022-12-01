package com.gramadsky.service.impl;

import com.gramadsky.model.entity.Login;
import com.gramadsky.model.repository.LoginRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class LoginServiceImplTest {
    @Autowired
    private LoginServiceImpl loginService;
    @MockBean
    private LoginRepository loginRepository;

    private static final Integer LOGIN_ID = 11;
    private static final String LOGIN_USERNAME = "frank";
    private static final String LOGIN_PASSWORD = "123456";
    private static final Login.Role LOGIN_ROLE = Login.Role.ROLE_CLIENT;

    @Test
    void saveTest() {
        Login login = new Login(LOGIN_ID, LOGIN_USERNAME, LOGIN_PASSWORD, LOGIN_ROLE);

        assertAll(
                () -> assertEquals(11, login.getId()),
                () -> assertEquals("frank", login.getUsername()),
                () -> assertEquals("123456", login.getPassword()),
                () -> assertEquals(Login.Role.ROLE_CLIENT, login.getRole())
        );
    }

    @Test
    void findAllTest() {
        when(loginRepository.findAll()).thenReturn(Stream
                .of(new Login(LOGIN_ID, LOGIN_USERNAME, LOGIN_PASSWORD, LOGIN_ROLE))
                .collect(Collectors.toList()));

        assertEquals(1, loginService.findAll().size());
    }

    @Test
    void deleteLoginTest() {
        Login login = new Login(LOGIN_ID, LOGIN_USERNAME, LOGIN_PASSWORD, LOGIN_ROLE);
        loginService.delete(login);
        verify(loginRepository, times(1)).delete(login);
    }
}