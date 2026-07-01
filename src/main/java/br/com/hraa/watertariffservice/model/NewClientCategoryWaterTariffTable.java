package br.com.hraa.watertariffservice.model;

import java.util.List;

public class NewClientCategoryWaterTariffTable {
    private String clientCategoryName;

    private List<NewWaterTariffTableRange> newWaterTariffTableRangeList;

    public NewClientCategoryWaterTariffTable(String clientCategoryName,
            List<NewWaterTariffTableRange> newWaterTariffTableRangeList) {
        this.clientCategoryName = clientCategoryName;
        this.newWaterTariffTableRangeList = newWaterTariffTableRangeList;
    }

    public String getClientCategoryName() {
        return clientCategoryName;
    }

    public List<NewWaterTariffTableRange> getNewWaterTariffTableRangeList() {
        return newWaterTariffTableRangeList;
    }
}
