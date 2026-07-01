package br.com.hraa.watertariffservice.dto;

import br.com.hraa.watertariffservice.model.NewClientCategoryWaterTariffTable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CreateClientCategoryWaterTariffTablesRequestDto {
    @JsonProperty("tabelas")
    private List<ClientCategoryWaterTariffTableDto> clientCategoryWaterTariffTableList;

    public CreateClientCategoryWaterTariffTablesRequestDto(
        List<ClientCategoryWaterTariffTableDto> clientCategoryWaterTariffTableList
    ) {
        this.clientCategoryWaterTariffTableList = clientCategoryWaterTariffTableList;
    }

    public List<ClientCategoryWaterTariffTableDto> getClientCategoryWaterTariffTableList() {
        return clientCategoryWaterTariffTableList;
    }

    public List<NewClientCategoryWaterTariffTable> toNewClientCategoryWaterTariffTableList() {
        var newCategoryTariffTableList = new ArrayList<NewClientCategoryWaterTariffTable>();
        clientCategoryWaterTariffTableList.forEach(
                categoryTariffTableDto ->
                        newCategoryTariffTableList.add(
                                categoryTariffTableDto.toNewClientCategoryWaterTariffTable())
        );
        return newCategoryTariffTableList;
    }
}
