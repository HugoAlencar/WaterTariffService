package br.com.hraa.watertariffservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO: Use Spring Cloud configuration server to get configuration dynamically.
// TODO: Enable Actuator and Admin server to monitor service metrics.
@SpringBootApplication
public class WaterTariffServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(WaterTariffServiceApplication.class, args);
	}
}
