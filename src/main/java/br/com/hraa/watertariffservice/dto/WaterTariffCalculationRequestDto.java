package br.com.hraa.watertariffservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WaterTariffCalculationRequestDto {
    @JsonProperty("categoria")
    private String clientCategoryName;

    @JsonProperty("consumo")
    private int consumedVolumeInM3;

    public String getClientCategoryName() {
        return clientCategoryName;
    }

    public int getConsumedVolumeInM3() {
        return consumedVolumeInM3;
    }

    public void setClientCategoryName(String clientCategoryName) {
        this.clientCategoryName = clientCategoryName;
    }

    public void setConsumedVolumeInM3(int consumedVolumeInM3) {
        this.consumedVolumeInM3 = consumedVolumeInM3;
    }
}
