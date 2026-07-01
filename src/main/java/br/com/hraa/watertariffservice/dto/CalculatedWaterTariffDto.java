package br.com.hraa.watertariffservice.dto;

import br.com.hraa.watertariffservice.model.CalculatedWaterTariff;
import br.com.hraa.watertariffservice.util.NumberUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculatedWaterTariffDto {
    @JsonProperty("categoria")
    private String clientCategoryName;

    @JsonProperty("consumoTotal")
    private int consumedVolumeInM3;

    @JsonProperty("valorTotal")
    private BigDecimal chargedPrice;

    @JsonProperty("detalhamento")
    private List<CalculatedWaterTariffDetailsDto> waterTariffDetailsList;

    public CalculatedWaterTariffDto(String clientCategoryName, int consumedVolumeInM3,
                                    BigDecimal chargedPrice,
                                    List<CalculatedWaterTariffDetailsDto> waterTariffDetailsList) {
        this.clientCategoryName = clientCategoryName;
        this.consumedVolumeInM3 = consumedVolumeInM3;
        this.chargedPrice = chargedPrice;
        this.waterTariffDetailsList = waterTariffDetailsList;
    }

    public static CalculatedWaterTariffDto from(CalculatedWaterTariff waterTariff,
                                                String clientCategoryName) {
        var waterTariffDetailsDtoList = new ArrayList<CalculatedWaterTariffDetailsDto>();
        waterTariff.getWaterTariffDetailsByRangeList().forEach(
                detailsByRange ->
                        waterTariffDetailsDtoList.add(CalculatedWaterTariffDetailsDto.from(detailsByRange))
        );
        return new CalculatedWaterTariffDto(clientCategoryName, waterTariff.getConsumedVolumeInM3(),
                NumberUtils.getBigDecimalAmountFromCents(waterTariff.getChargedPriceInCents()),
                waterTariffDetailsDtoList);
    }

    public String getClientCategoryName() {
        return clientCategoryName;
    }

    public int getConsumedVolumeInM3() {
        return consumedVolumeInM3;
    }

    public BigDecimal getChargedPrice() {
        return chargedPrice;
    }

    public List<CalculatedWaterTariffDetailsDto> getWaterTariffDetailsList() {
        return waterTariffDetailsList;
    }
}
