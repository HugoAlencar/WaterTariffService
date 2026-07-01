package br.com.hraa.watertariffservice.service;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class WaterTariffServiceTestConfiguration {
    @Bean
    @Primary
    WaterTariffService waterTariffService() {
        return Mockito.mock(WaterTariffService.class);
    }
}
