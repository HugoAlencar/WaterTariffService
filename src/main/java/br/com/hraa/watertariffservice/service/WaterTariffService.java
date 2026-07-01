package br.com.hraa.watertariffservice.service;

import br.com.hraa.watertariffservice.model.CalculatedWaterTariff;
import br.com.hraa.watertariffservice.model.ClientCategoryWaterTariffTable;
import br.com.hraa.watertariffservice.model.NewClientCategoryWaterTariffTable;

import java.util.List;

public interface WaterTariffService {

    public List<Long> createClientCategoryWaterTariffTables(
            List<NewClientCategoryWaterTariffTable> newCategoryTariffTableList);

    public List<ClientCategoryWaterTariffTable> getAllClientCategoryWaterTariffTables();

    public void softDeleteWaterTariffTable(long id);

    public CalculatedWaterTariff calculateWaterTariff(String clientCategoryName, int consumedVolumeInM3);
}
