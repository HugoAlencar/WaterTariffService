package br.com.hraa.watertariffservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "water_tariff_table_range")
public class WaterTariffTableRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long waterTariffTableId;

    @Column(nullable = false)
    int rangeStartInM3;

    @Column
    Integer rangeEndInM3;

    @Column(nullable = false)
    long pricePerM3InCents;

    protected WaterTariffTableRange() { }

    public WaterTariffTableRange(long waterTariffTableId, int rangeStartInM3,
                                 Integer rangeEndInM3, long pricePerM3InCents) {
        this.waterTariffTableId = waterTariffTableId;
        this.rangeStartInM3 = rangeStartInM3;
        this.rangeEndInM3 = rangeEndInM3;
        this.pricePerM3InCents = pricePerM3InCents;
    }

    @Override
    public String toString() {
        return String.format(
            "WaterTariffTableRange[id=%d, waterTariffTableId=%d rangeStartInM3=%d, "
                + "rangeEndInM3=%d, pricePerM3InCents=%d]",
            id, waterTariffTableId, rangeStartInM3, rangeEndInM3, pricePerM3InCents);
    }

    public long getId() {
        return id;
    }

    public long getWaterTariffTableId() {
        return waterTariffTableId;
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
