package br.com.hraa.watertariffservice.service;

import br.com.hraa.watertariffservice.model.CalculatedWaterTariff;
import br.com.hraa.watertariffservice.model.WaterTariffTableRange;

import java.util.List;

public interface WaterTariffCalculator {
    CalculatedWaterTariff calculateWaterTariffForRanges(
        List<WaterTariffTableRange> waterTariffTableRangeList,
        int consumedVolumeInM3);
}
