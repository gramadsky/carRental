package com.gramadsky.service.impl;

import com.gramadsky.model.entity.CarStatus;
import com.gramadsky.model.repository.CarStatusRepository;
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
class CarStatusServiceImplTest {
    @Autowired
    private CarStatusServiceImpl carStatusService;
    @MockBean
    private CarStatusRepository carStatusRepository;

    @Test
    void findAll() {

        when(carStatusRepository.findAll()).thenReturn(Stream
                .of(new CarStatus(1, "available"), new CarStatus(2, "ordered"),
                        new CarStatus(3, "on repair"))
                .collect(Collectors.toList()));

        assertEquals(3, carStatusService.findAll().size());
    }
}