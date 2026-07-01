package br.com.hraa.watertariffservice.service;

import br.com.hraa.watertariffservice.model.CalculatedWaterTariff;
import br.com.hraa.watertariffservice.model.CalculatedWaterTariffDetailsByRange;
import br.com.hraa.watertariffservice.model.WaterTariffTableRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

@SpringBootTest
@ActiveProfiles("test")
public class WaterTariffCalculatorTests {
    @Autowired
    private WaterTariffCalculator waterTariffCalculator;

    @Test
    public void calculateWaterTariffForRanges_calculatesForConsumedVolumeAndAllRanges() {
        var consumedVolumeInM3 = 35;
        var tariffTableRangeList = new ArrayList<WaterTariffTableRange>();

        tariffTableRangeList.add(
            new WaterTariffTableRange(1, 0, 10, 617L));
        tariffTableRangeList.add(
                new WaterTariffTableRange(1, 11, 20, 419L));
        tariffTableRangeList.add(
                new WaterTariffTableRange(1, 21, 30, 450L));
        tariffTableRangeList.add(
                new WaterTariffTableRange(1, 31, null, 500L));

        var expectedCalculatedTariffDetailsByRangeList = new ArrayList<CalculatedWaterTariffDetailsByRange>();
        expectedCalculatedTariffDetailsByRangeList.add(
            new CalculatedWaterTariffDetailsByRange(0, 10, 10, 617L, 6170L));
        expectedCalculatedTariffDetailsByRangeList.add(
            new CalculatedWaterTariffDetailsByRange(11, 20, 10, 419L, 4190L));
        expectedCalculatedTariffDetailsByRangeList.add(
            new CalculatedWaterTariffDetailsByRange(21, 30, 10, 450L, 4500L));
        expectedCalculatedTariffDetailsByRangeList.add(
                new CalculatedWaterTariffDetailsByRange(31, null, 5, 500L, 2500L));

        var expectedChargedPriceInCents = 17360L;
        CalculatedWaterTariff expectedCalculatedWaterTariff = new CalculatedWaterTariff(35,
            expectedChargedPriceInCents, expectedCalculatedTariffDetailsByRangeList);

        var actualCalculatedWaterTariff = waterTariffCalculator.calculateWaterTariffForRanges(
            tariffTableRangeList, consumedVolumeInM3);
        Assertions.assertEquals(expectedCalculatedWaterTariff, actualCalculatedWaterTariff);
    }

    @Test
    public void calculateWaterTariffForRanges_returnsDetailsOnlyForTheReachedRanges() {
        var consumedVolumeInM3 = 16;
        var tariffTableRangeList = new ArrayList<WaterTariffTableRange>();

        tariffTableRangeList.add(
                new WaterTariffTableRange(1, 0, 10, 617L));
        tariffTableRangeList.add(
                new WaterTariffTableRange(1, 11, 20, 419L));
        tariffTableRangeList.add(
                new WaterTariffTableRange(1, 21, 30, 450L));
        tariffTableRangeList.add(
                new WaterTariffTableRange(1, 31, null, 500L));

        var expectedCalculatedTariffDetailsByRangeList = new ArrayList<CalculatedWaterTariffDetailsByRange>();
        expectedCalculatedTariffDetailsByRangeList.add(
                new CalculatedWaterTariffDetailsByRange(0, 10, 10, 617L, 6170L));
        expectedCalculatedTariffDetailsByRangeList.add(
                new CalculatedWaterTariffDetailsByRange(11, 20, 6, 419L, 2514L));

        var expectedChargedPriceInCents = 8684L;
        CalculatedWaterTariff expectedCalculatedWaterTariff = new CalculatedWaterTariff(16,
                expectedChargedPriceInCents, expectedCalculatedTariffDetailsByRangeList);

        var actualCalculatedWaterTariff = waterTariffCalculator.calculateWaterTariffForRanges(
                tariffTableRangeList, consumedVolumeInM3);
        Assertions.assertEquals(expectedCalculatedWaterTariff, actualCalculatedWaterTariff);
    }
}
