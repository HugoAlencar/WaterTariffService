package br.com.hraa.watertariffservice.dto;

import br.com.hraa.watertariffservice.model.ClientCategoryWaterTariffTable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GetAllClientCategoryWaterTariffTablesResponseDto {
    @JsonProperty("tabelas")
    private List<ClientCategoryWaterTariffTableDto> clientCategoryWaterTariffTableList;

    public GetAllClientCategoryWaterTariffTablesResponseDto(
            List<ClientCategoryWaterTariffTableDto> clientCategoryWaterTariffTableList) {
        this.clientCategoryWaterTariffTableList = clientCategoryWaterTariffTableList;
    }

    public static GetAllClientCategoryWaterTariffTablesResponseDto from(
            List<ClientCategoryWaterTariffTable> clientCategoryWaterTariffTableList) {
        var categoryTariffTableDtoList = new ArrayList<ClientCategoryWaterTariffTableDto>();
        clientCategoryWaterTariffTableList.forEach(
                categoryTariffTable ->
                        categoryTariffTableDtoList.add(
                                ClientCategoryWaterTariffTableDto.from(categoryTariffTable))
        );
        return new GetAllClientCategoryWaterTariffTablesResponseDto(categoryTariffTableDtoList);
    }

    public List<ClientCategoryWaterTariffTableDto> getClientCategoryWaterTariffTableList() {
        return clientCategoryWaterTariffTableList;
    }
}
