package com.gramadsky.service.impl;

import com.gramadsky.model.entity.User;
import com.gramadsky.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;
    @MockBean
    private UserRepository userRepository;

    private static final String USER_NAME = "Rob";
    private static final String USER_SURNAME = "Danst";
    private static final LocalDate USER_DATEOFBIRTH = LocalDate.of(2000, 01, 01);
    private static final String USER_EMAIL = "rob@gmail.com";
    private static final String USER_PASSPORT = "KE8902348";
    private static final User.Status USER_STATUS = User.Status.NO_DEBTS;

    @Test
    void saveUserTest() {
        User user = new User(USER_NAME, USER_SURNAME, USER_DATEOFBIRTH, USER_EMAIL, USER_PASSPORT, USER_STATUS);

        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.save(user));

        Mockito.verify(userRepository, Mockito.timeout(1)).save(user);

        assertAll(
                () -> assertEquals("Rob", user.getName()),
                () -> assertEquals("Danst", user.getSurname()),
                () -> assertEquals(LocalDate.of(2000, 1, 1), user.getDateOfBirth()),
                () -> assertEquals("rob@gmail.com", user.getEmail()),
                () -> assertEquals("KE8902348", user.getPassportData()),
                () -> assertEquals(User.Status.NO_DEBTS, user.getStatus())
        );
    }

    @Test
    void findAllTest() {
        when(userRepository.findAll()).thenReturn(Stream
                .of(new User(USER_NAME, USER_SURNAME, USER_DATEOFBIRTH, USER_EMAIL, USER_PASSPORT, USER_STATUS))
                .collect(Collectors.toList()));

        assertEquals(1, userService.findAll().size());
    }

    @Test
    void deleteUserTest() {
        User user = new User(USER_NAME, USER_SURNAME, USER_DATEOFBIRTH, USER_EMAIL, USER_PASSPORT, USER_STATUS);
        userService.deleteUser(user);
        verify(userRepository, times(1)).delete(user);
    }
}