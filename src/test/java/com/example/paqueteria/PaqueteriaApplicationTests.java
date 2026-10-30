package com.example.paqueteria;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Disabled;
@SpringBootTest
@Disabled("Necesita PostgreSQL, RabbitMQ y application.properties. "
        + "Se sustituirá por tests de integración con Testcontainers.")
class PaqueteriaApplicationTests {

	@Test
	void contextLoads() {
	}

}
