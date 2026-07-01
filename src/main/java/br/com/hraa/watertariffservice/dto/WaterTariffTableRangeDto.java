package br.com.hraa.watertariffservice.dto;

import br.com.hraa.watertariffservice.model.NewWaterTariffTableRange;
import br.com.hraa.watertariffservice.model.WaterTariffTableRange;
import br.com.hraa.watertariffservice.util.NumberUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class WaterTariffTableRangeDto {
    @JsonProperty("faixa")
    private WaterTariffRangeLimitsDto rangeLimits;

    @JsonProperty("valorUnitario")
    private BigDecimal priceByM3;

    public WaterTariffTableRangeDto(WaterTariffRangeLimitsDto rangeLimits, BigDecimal priceByM3) {
        this.rangeLimits = rangeLimits;
        this.priceByM3 = priceByM3;
    }

    public static WaterTariffTableRangeDto from(WaterTariffTableRange waterTariffTableRange) {
        var rangeLimitsDto = WaterTariffRangeLimitsDto.from(waterTariffTableRange);
        return new WaterTariffTableRangeDto(rangeLimitsDto,
            NumberUtils.getBigDecimalAmountFromCents(waterTariffTableRange.getPricePerM3InCents()));
    }

    public NewWaterTariffTableRange toNewWaterTariffTableRange() {
        return new NewWaterTariffTableRange(rangeLimits.getRangeStartInM3(),
            rangeLimits.getRangeEndInM3(),
            NumberUtils.getCentsFromBigDecimalAmount(priceByM3));
    }

    public WaterTariffRangeLimitsDto getRangeLimits() {
        return rangeLimits;
    }

    public BigDecimal getPriceByM3() {
        return priceByM3;
    }
}
