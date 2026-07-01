package br.com.hraa.watertariffservice.dto;

import br.com.hraa.watertariffservice.model.ClientCategoryWaterTariffTable;
import br.com.hraa.watertariffservice.model.NewClientCategoryWaterTariffTable;
import br.com.hraa.watertariffservice.model.NewWaterTariffTableRange;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ClientCategoryWaterTariffTableDto {
    @JsonProperty("categoria")
    private String clientCategoryName;

    @JsonProperty("tabela")
    private List<WaterTariffTableRangeDto> waterTariffTableRangeList;

    public ClientCategoryWaterTariffTableDto(String clientCategoryName,
            List<WaterTariffTableRangeDto> waterTariffTableRanges) {
        this.clientCategoryName = clientCategoryName;
        this.waterTariffTableRangeList = waterTariffTableRanges;
    }

    public static ClientCategoryWaterTariffTableDto from(
            ClientCategoryWaterTariffTable clientCategoryWaterTariffTable
    ) {
        var tariffTableRangeDtoList = new ArrayList<WaterTariffTableRangeDto>();
        clientCategoryWaterTariffTable.getWaterTariffTableRangeList().forEach(
                range -> tariffTableRangeDtoList.add(WaterTariffTableRangeDto.from(range))
        );
        var clientCategory = clientCategoryWaterTariffTable.getClientCategory();
        return new ClientCategoryWaterTariffTableDto(clientCategory.getName(),
            tariffTableRangeDtoList);
    }

    public String getClientCategoryName() {
        return clientCategoryName;
    }

    public List<WaterTariffTableRangeDto> getWaterTariffTableRangeList() {
        return waterTariffTableRangeList;
    }

    public NewClientCategoryWaterTariffTable toNewClientCategoryWaterTariffTable() {
        var newTariffTableRangeList = new ArrayList<NewWaterTariffTableRange>();
        waterTariffTableRangeList.forEach(waterTariffTableRangeDto ->
                newTariffTableRangeList.add(waterTariffTableRangeDto.toNewWaterTariffTableRange()));
        return new NewClientCategoryWaterTariffTable(clientCategoryName, newTariffTableRangeList);
    }
}
