package com.gramadsky.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AdminControllerTest {
    @Autowired
    private AdminController controller;

    //    that the context is creating your controller
    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
