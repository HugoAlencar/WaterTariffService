package br.com.hraa.watertariffservice.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootTest
@ActiveProfiles("test")
public class NumberUtilsTests {

    @Test
    public void getCents_withValidValues_returnsCents() {
        var expectedCents1 = 123L;
        var amount1 = new BigDecimal("1.23").setScale(2, RoundingMode.HALF_EVEN);
        Assertions.assertEquals(expectedCents1, NumberUtils.getCentsFromBigDecimalAmount(amount1));

        var expectedCents2 = 99999L;
        var amount2 = new BigDecimal("999.99").setScale(2, RoundingMode.HALF_EVEN);
        Assertions.assertEquals(expectedCents2, NumberUtils.getCentsFromBigDecimalAmount(amount2));
    }

    @Test
    public void getCents_withFractionOfCents_doesBankersRounding() {
        var expectedCents1 = 100L;
        var amount1 = new BigDecimal("1.005").setScale(2, RoundingMode.HALF_EVEN);
        Assertions.assertEquals(expectedCents1, NumberUtils.getCentsFromBigDecimalAmount(amount1));

        var expectedCents2 = 201L;
        var amount2 = new BigDecimal("2.009").setScale(2, RoundingMode.HALF_EVEN);
        Assertions.assertEquals(expectedCents2, NumberUtils.getCentsFromBigDecimalAmount(amount2));

        var expectedCents3 = 301L;
        var amount3 = new BigDecimal("3.014").setScale(2, RoundingMode.HALF_EVEN);
        Assertions.assertEquals(expectedCents3, NumberUtils.getCentsFromBigDecimalAmount(amount3));

        var expectedCents4 = 402L;
        var amount4 = new BigDecimal("4.015").setScale(2, RoundingMode.HALF_EVEN);
        Assertions.assertEquals(expectedCents4, NumberUtils.getCentsFromBigDecimalAmount(amount4));
    }

    @Test
    public void getBigDecimalAmount_withValidValues_returnsCorrectBigDecimal() {
        var expectedAmount1 = new BigDecimal("0.01").setScale(2, RoundingMode.HALF_EVEN);
        var cents1 = 1L;
        Assertions.assertEquals(expectedAmount1, NumberUtils.getBigDecimalAmountFromCents(cents1));

        var expectedAmount2 = new BigDecimal("9999.99").setScale(2, RoundingMode.HALF_EVEN);
        var cents2 = 999999L;
        Assertions.assertEquals(expectedAmount2, NumberUtils.getBigDecimalAmountFromCents(cents2));
    }
}
