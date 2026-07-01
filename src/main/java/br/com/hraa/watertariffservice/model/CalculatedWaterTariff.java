package br.com.hraa.watertariffservice.model;

import java.util.List;

public class CalculatedWaterTariff {
    private int consumedVolumeInM3;

    private long chargedPriceInCents;

    private List<CalculatedWaterTariffDetailsByRange> waterTariffDetailsByRangeList;

    public CalculatedWaterTariff(int consumedVolumeInM3, long chargedPriceInCents,
                       List<CalculatedWaterTariffDetailsByRange> waterTariffDetailsByRangeList) {
        this.consumedVolumeInM3 = consumedVolumeInM3;
        this.chargedPriceInCents = chargedPriceInCents;
        this.waterTariffDetailsByRangeList = waterTariffDetailsByRangeList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CalculatedWaterTariff other) {
            if (consumedVolumeInM3 != other.getConsumedVolumeInM3()
                    || chargedPriceInCents != other.getChargedPriceInCents()) {
                return false;
            }
            var otherWaterTariffDetailsByRangeList = other.getWaterTariffDetailsByRangeList();
            if (waterTariffDetailsByRangeList.size() != otherWaterTariffDetailsByRangeList.size()) {
                return false;
            }
            for (int i = 0; i < waterTariffDetailsByRangeList.size(); i++) {
                var waterTariffDetailsByRange = waterTariffDetailsByRangeList.get(i);
                var otherWaterTariffDetailsByRange = otherWaterTariffDetailsByRangeList.get(i);
                if (!waterTariffDetailsByRange.equals(otherWaterTariffDetailsByRange)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public int getConsumedVolumeInM3() {
        return consumedVolumeInM3;
    }

    public long getChargedPriceInCents() {
        return chargedPriceInCents;
    }

    public List<CalculatedWaterTariffDetailsByRange> getWaterTariffDetailsByRangeList() {
        return waterTariffDetailsByRangeList;
    }
}
