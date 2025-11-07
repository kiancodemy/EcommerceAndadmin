package com.Main.Ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("dev")
@TestPropertySource(locations = "classpath:application-test.properties")
class EcommerceApplicationTests {

	@Test
	void contextLoads() {
	}

}
