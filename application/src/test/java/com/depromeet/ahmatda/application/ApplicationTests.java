package com.depromeet.ahmatda.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.depromeet.ahmatda.Application;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}
