package br.com.hraa.watertariffservice.model;

public class NewWaterTariffTableRange {
    int rangeStartInM3;

    Integer rangeEndInM3;

    long pricePerM3InCents;

    public NewWaterTariffTableRange(int rangeStartInM3, Integer rangeEndInM3,
                                    long pricePerM3InCents) {
        this.rangeStartInM3 = rangeStartInM3;
        this.rangeEndInM3 = rangeEndInM3;
        this.pricePerM3InCents = pricePerM3InCents;
    }

    public int getRangeStartInM3() {
        return rangeStartInM3;
    }

    public Integer getRangeEndInM3() {
        return rangeEndInM3;
    }

    public long getPricePerM3InCents() {
        return pricePerM3InCents;
    }
}
