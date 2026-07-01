package br.com.hraa.watertariffservice.dto;

import br.com.hraa.watertariffservice.model.CalculatedWaterTariffDetailsByRange;
import br.com.hraa.watertariffservice.model.WaterTariffTableRange;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WaterTariffRangeLimitsDto {
    @JsonProperty("inicio")
    private int rangeStartInM3;

    @JsonProperty("fim")
    private Integer rangeEndInM3;

    public WaterTariffRangeLimitsDto(int rangeStartInM3, Integer rangeEndInM3) {
        this.rangeStartInM3 = rangeStartInM3;
        // this.rangeEndInM3 = Objects.requireNonNullElse(rangeEndInM3, 99999);
        this.rangeEndInM3 = rangeEndInM3;
    }

    public static WaterTariffRangeLimitsDto from(WaterTariffTableRange waterTariffTableRange) {
        return new WaterTariffRangeLimitsDto(waterTariffTableRange.getRangeStartInM3(),
                waterTariffTableRange.getRangeEndInM3());
    }

    public static WaterTariffRangeLimitsDto from(
            CalculatedWaterTariffDetailsByRange waterTariffDetailsByRange) {
        return new WaterTariffRangeLimitsDto(waterTariffDetailsByRange.getRangeStartInM3(),
                waterTariffDetailsByRange.getRangeEndInM3());
    }

    public int getRangeStartInM3() {
        return rangeStartInM3;
    }

    public Integer getRangeEndInM3() {
        return rangeEndInM3;
    }
}
