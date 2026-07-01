package br.com.hraa.watertariffservice.dto;

import br.com.hraa.watertariffservice.model.CalculatedWaterTariffDetailsByRange;
import br.com.hraa.watertariffservice.util.NumberUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CalculatedWaterTariffDetailsDto {
    @JsonProperty("faixa")
    private WaterTariffRangeLimitsDto waterTariffDetailsRange;

    @JsonProperty("m3Cobrados")
    private int chargedVolumeInM3;

    @JsonProperty("valorUnitario")
    private BigDecimal priceByM3InCents;

    @JsonProperty("subtotal")
    private BigDecimal priceChargedInCents;

    public CalculatedWaterTariffDetailsDto(WaterTariffRangeLimitsDto waterTariffDetailsRange,
                                           int chargedVolumeInM3, BigDecimal priceByM3InCents,
                                           BigDecimal priceChargedInCents) {
        this.waterTariffDetailsRange = waterTariffDetailsRange;
        this.chargedVolumeInM3 = chargedVolumeInM3;
        this.priceByM3InCents = priceByM3InCents;
        this.priceChargedInCents = priceChargedInCents;
    }

    public static CalculatedWaterTariffDetailsDto from(
            CalculatedWaterTariffDetailsByRange waterTariffDetailsByRange) {
        var waterTariffDetailsRangeDto = WaterTariffRangeLimitsDto.from(waterTariffDetailsByRange);
        return new CalculatedWaterTariffDetailsDto(waterTariffDetailsRangeDto,
                waterTariffDetailsByRange.getChargedVolumeInM3(),
                NumberUtils.getBigDecimalAmountFromCents(
                    waterTariffDetailsByRange.getPriceByM3InCents()),
                NumberUtils.getBigDecimalAmountFromCents(
                    waterTariffDetailsByRange.getPriceChargedInCents()));
    }

    public WaterTariffRangeLimitsDto getWaterTariffDetailsRange() {
        return waterTariffDetailsRange;
    }

    public int getChargedVolumeInM3() {
        return chargedVolumeInM3;
    }

    public BigDecimal getPriceByM3InCents() {
        return priceByM3InCents;
    }

    public BigDecimal getPriceChargedInCents() {
        return priceChargedInCents;
    }
}
