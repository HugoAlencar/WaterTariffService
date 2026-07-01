package br.com.hraa.watertariffservice.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {
    private static final BigDecimal oneHundred = new BigDecimal(100)
        .setScale(2, RoundingMode.HALF_EVEN);

    public static BigDecimal getBigDecimalAmountFromCents(long cents) {
        return new BigDecimal(cents).setScale(2, RoundingMode.HALF_EVEN)
            .divide(oneHundred, RoundingMode.HALF_EVEN);
    }

    public static long getCentsFromBigDecimalAmount(BigDecimal decimalMoney) {
        return decimalMoney.multiply(oneHundred).longValue();
    }
}
