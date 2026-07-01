package br.com.hraa.watertariffservice.validation;

import br.com.hraa.watertariffservice.dto.ClientCategoryWaterTariffTableDto;
import br.com.hraa.watertariffservice.dto.WaterTariffTableRangeDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientCategoryWaterTariffTableDtoListValidator {
    public static void validateList(List<ClientCategoryWaterTariffTableDto> categoryWaterTariffTableList) {
        // Validates the table list
        if (categoryWaterTariffTableList == null || categoryWaterTariffTableList.isEmpty()) {
            throw new IllegalArgumentException("The file must contain at least one tariff table.");
        }

        // For checking if there's any repeated client category.
        Set<String> categorySet = new HashSet<String>();

        // Validates each table
        for (int i = 0; i < categoryWaterTariffTableList.size(); i++) {
            var clientCategoryWaterTariffTable = categoryWaterTariffTableList.get(i);
            // Validates the table's client category
            var clientCategoryName = clientCategoryWaterTariffTable.getClientCategoryName();
            if (clientCategoryName == null || clientCategoryName.isBlank()) {
                throw new IllegalArgumentException("The table must have a valid client category.");
            }

            // Checks if the category had already appeared in this list
            if (categorySet.contains(clientCategoryName)) {
                throw new IllegalArgumentException("Each client category must occur at most once in a batch.");
            } else {
                categorySet.add(clientCategoryName);
            }

            // Validates the table's range list
            var waterTariffTableRangeList = clientCategoryWaterTariffTable.getWaterTariffTableRangeList();
            validate(waterTariffTableRangeList);
        }
    }

    public static void validate(List<WaterTariffTableRangeDto> waterTariffTableRangeList) {
        if (waterTariffTableRangeList == null || waterTariffTableRangeList.isEmpty()) {
            throw new IllegalArgumentException("The table must have at least one range.");
        }

        // Validates the ranges
        var waterTariffTableRangeListSize = waterTariffTableRangeList.size();
        var expectedNextRangeStart = 0;
        for (int i = 0; i < waterTariffTableRangeListSize; i++) {
            var waterTariffTableRange = waterTariffTableRangeList.get(i);
            // Validates the range's price
            if (waterTariffTableRange.getPriceByM3().signum() < 0) {
                throw new IllegalArgumentException("The price must be non-negative.");
            }

            // Validates the range's limits
            var rangeLimits = waterTariffTableRange.getRangeLimits();
            var rangeStart = rangeLimits.getRangeStartInM3();
            var rangeEnd = rangeLimits.getRangeEndInM3();
            if (i == 0 && rangeStart != 0) {
                throw new IllegalArgumentException("The first range must start in 0 m3.");
            }
            if (rangeStart != expectedNextRangeStart) {
                throw new IllegalArgumentException(
                        "Every range must continue right after the previous range's end.");
            }
            if (rangeEnd == null) {
                if (i != waterTariffTableRangeListSize - 1) {
                    throw new IllegalArgumentException("Every range's end except the last's must be set.");
                }
            } else {
                if (i == waterTariffTableRangeListSize - 1) {
                    throw new IllegalArgumentException("The last range's end must not be set.");
                }
                if (rangeStart > rangeEnd) {
                    throw new IllegalArgumentException(
                            "The range's start must not be greater than the range's end.");
                }
                expectedNextRangeStart = rangeEnd + 1;
            }
        }
    }
}
