package br.com.hraa.watertariffservice.controller;

import br.com.hraa.watertariffservice.service.WaterTariffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class WaterTariffControllerTests {
    @Autowired
    private WaterTariffController waterTariffController;

    @Autowired
    private WaterTariffService waterTariffService;

    @Test
    public void deleteWaterTariffTable_deletesSuccessfully() {
        Mockito.doNothing().when(waterTariffService).softDeleteWaterTariffTable(2L);
        waterTariffController.deleteWaterTariffTable(2L);
        Mockito.verify(waterTariffService, Mockito.times(1)).softDeleteWaterTariffTable(2L);
    }
}
