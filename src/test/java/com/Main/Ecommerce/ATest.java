package com.Main.Ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ATest {
    @Value("${kian.name}")
    private String kianName;


    @Test
    public void contextLoads() {
        System.out.println(kianName);

    }
}
