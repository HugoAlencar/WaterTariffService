package br.com.hraa.watertariffservice.service;

import br.com.hraa.watertariffservice.model.CalculatedWaterTariff;
import br.com.hraa.watertariffservice.model.CalculatedWaterTariffDetailsByRange;
import br.com.hraa.watertariffservice.model.WaterTariffTableRange;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WaterTariffCalculatorImpl implements WaterTariffCalculator {
    @Override
    public CalculatedWaterTariff calculateWaterTariffForRanges(
            List<WaterTariffTableRange> waterTariffTableRangeList,
            int consumedVolumeInM3) {
        var remainingVolumeConsumed = consumedVolumeInM3;
        var totalTariffInCents = 0L;
        var waterTariffDetailsByRangeList = new ArrayList<CalculatedWaterTariffDetailsByRange>();
        for (int i = 0; i < waterTariffTableRangeList.size() && remainingVolumeConsumed > 0; i++) {
            var tariffDetails = calculatePartialWaterTariffForRange(
                    waterTariffTableRangeList.get(i), remainingVolumeConsumed);
            totalTariffInCents += tariffDetails.getPriceChargedInCents();
            remainingVolumeConsumed -= tariffDetails.getChargedVolumeInM3();
            waterTariffDetailsByRangeList.add(tariffDetails);
        }
        return new CalculatedWaterTariff(consumedVolumeInM3, totalTariffInCents,
            waterTariffDetailsByRangeList);
    }

    private CalculatedWaterTariffDetailsByRange calculatePartialWaterTariffForRange(
            WaterTariffTableRange waterTariffTableRange, int consumedVolumeInM3) {
        var consumedVolumeForRange = consumedVolumeInM3;
        var volumeRangeEnd = waterTariffTableRange.getRangeEndInM3();
        var volumeRangeStart = waterTariffTableRange.getRangeStartInM3();
        if (volumeRangeEnd != null) {
            if (volumeRangeStart == 0) {
                volumeRangeStart = 1;
            }
            var volumeLimitForRange = volumeRangeEnd - volumeRangeStart + 1;
            if (volumeLimitForRange < consumedVolumeInM3) {
                consumedVolumeForRange = volumeLimitForRange;
            }
        }
        var tariffPriceInCents = waterTariffTableRange.getPricePerM3InCents() * consumedVolumeForRange;
        return new CalculatedWaterTariffDetailsByRange(waterTariffTableRange.getRangeStartInM3(),
            volumeRangeEnd, consumedVolumeForRange, waterTariffTableRange.getPricePerM3InCents(),
            tariffPriceInCents);
    }
}
