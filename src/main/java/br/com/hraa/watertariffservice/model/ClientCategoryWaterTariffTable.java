package br.com.hraa.watertariffservice.model;

import java.util.List;

public class ClientCategoryWaterTariffTable {
    private long id;

    private ClientCategory clientCategory;

    private List<WaterTariffTableRange> waterTariffTableRangeList;

    public ClientCategoryWaterTariffTable(long id, ClientCategory clientCategory,
                                          List<WaterTariffTableRange> waterTariffTableRangeList) {
        this.id = id;
        this.clientCategory = clientCategory;
        this.waterTariffTableRangeList = waterTariffTableRangeList;
    }

    public long getId() {
        return id;
    }

    public ClientCategory getClientCategory() {
        return clientCategory;
    }

    public List<WaterTariffTableRange> getWaterTariffTableRangeList() {
        return waterTariffTableRangeList;
    }
}
