package com.gramadsky.service.impl;

import com.gramadsky.model.entity.CarClass;
import com.gramadsky.model.repository.CarClassRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class CarClassServiceImplTest {
    @Autowired
    private CarClassServiceImpl carClassService;
    @MockBean
    private CarClassRepository carClassRepository;

    @Test
    void findAll() {

        when(carClassRepository.findAll()).thenReturn(Stream
                .of(new CarClass(1, "Economy"), new CarClass(2, "premium"))
                .collect(Collectors.toList()));

        assertEquals(2, carClassService.findAll().size());
    }
}