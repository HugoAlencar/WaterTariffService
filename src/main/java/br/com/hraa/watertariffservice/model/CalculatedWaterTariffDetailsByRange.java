package br.com.hraa.watertariffservice.model;

public class CalculatedWaterTariffDetailsByRange {
    private int rangeStartInM3;
    private Integer rangeEndInM3;
    private int chargedVolumeInM3;
    private long priceByM3InCents;
    private long priceChargedInCents;

    public CalculatedWaterTariffDetailsByRange(int rangeStartInM3, Integer rangeEndInM3,
                                     int chargedVolumeInM3, long priceByM3InCents,
                                     long priceChargedInCents) {
        this.rangeStartInM3 = rangeStartInM3;
        this.rangeEndInM3 = rangeEndInM3;
        this.chargedVolumeInM3 = chargedVolumeInM3;
        this.priceByM3InCents = priceByM3InCents;
        this.priceChargedInCents = priceChargedInCents;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CalculatedWaterTariffDetailsByRange other) {
            return (rangeStartInM3 == other.rangeStartInM3) && (rangeEndInM3 == other.rangeEndInM3)
                && (chargedVolumeInM3 == other.chargedVolumeInM3)
                && (priceByM3InCents == other.priceByM3InCents)
                && (priceChargedInCents == other.priceChargedInCents);
        }
        return false;
    }

    public int getRangeStartInM3() {
        return rangeStartInM3;
    }

    public Integer getRangeEndInM3() {
        return rangeEndInM3;
    }

    public int getChargedVolumeInM3() {
        return chargedVolumeInM3;
    }

    public long getPriceByM3InCents() {
        return priceByM3InCents;
    }

    public long getPriceChargedInCents() {
        return priceChargedInCents;
    }
}
