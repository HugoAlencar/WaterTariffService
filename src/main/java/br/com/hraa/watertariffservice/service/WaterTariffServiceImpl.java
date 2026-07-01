package br.com.hraa.watertariffservice.service;

import br.com.hraa.watertariffservice.exception.ClientCategoryException;
import br.com.hraa.watertariffservice.model.CalculatedWaterTariff;
import br.com.hraa.watertariffservice.model.ClientCategoryWaterTariffTable;
import br.com.hraa.watertariffservice.model.NewClientCategoryWaterTariffTable;
import br.com.hraa.watertariffservice.model.NewWaterTariffTableRange;
import br.com.hraa.watertariffservice.model.WaterTariffTable;
import br.com.hraa.watertariffservice.model.WaterTariffTableRange;
import br.com.hraa.watertariffservice.repository.ClientCategoryRepository;
import br.com.hraa.watertariffservice.repository.WaterTariffTableRangeRepository;
import br.com.hraa.watertariffservice.repository.WaterTariffTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WaterTariffServiceImpl implements WaterTariffService {
    private final ClientCategoryRepository clientCategoryRepository;
    private final WaterTariffTableRepository waterTariffTableRepository;
    private final WaterTariffTableRangeRepository waterTariffTableRangeRepository;
    private final WaterTariffCalculator waterTariffCalculator;

    public WaterTariffServiceImpl(ClientCategoryRepository clientCategoryRepository,
                                  WaterTariffTableRepository waterTariffTableRepository,
                                  WaterTariffTableRangeRepository waterTariffTableRangeRepository,
                                  WaterTariffCalculator waterTariffCalculator) {
        this.clientCategoryRepository = clientCategoryRepository;
        this.waterTariffTableRepository = waterTariffTableRepository;
        this.waterTariffTableRangeRepository = waterTariffTableRangeRepository;
        this.waterTariffCalculator = waterTariffCalculator;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public List<Long> createClientCategoryWaterTariffTables(
            List<NewClientCategoryWaterTariffTable> newCategoryTariffTableList) {
        var nowUtc = LocalDateTime.now(ZoneOffset.UTC);
        var newTariffTableIdList = new ArrayList<Long>();
        for (int i = 0; i < newCategoryTariffTableList.size(); i++) {
            var newCategoryTariffTable = newCategoryTariffTableList.get(i);
            Long newTariffTableId = createWaterTariffTable(newCategoryTariffTable, nowUtc);
            newTariffTableIdList.add(newTariffTableId);
        }
        return newTariffTableIdList;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    private long createWaterTariffTable(NewClientCategoryWaterTariffTable newCategoryTariffTable,
            LocalDateTime dateTimeUtc) {
        var clientCategoryName = newCategoryTariffTable.getClientCategoryName();
        var clientCategory = clientCategoryRepository.findByName(clientCategoryName);
        if (clientCategory == null) {
            throw new ClientCategoryException("There is no client category "
                    + clientCategoryName);
        }
        var clientCategoryId = clientCategory.getId();

        softDeleteWaterTariffTableByClientCategoryId(clientCategoryId, dateTimeUtc);

        var newWaterTariffTable = new WaterTariffTable(clientCategoryId, dateTimeUtc, null);
        var insertedWaterTariffTable = waterTariffTableRepository.saveAndFlush(newWaterTariffTable);

        createWaterTariffTableRanges(insertedWaterTariffTable.getId(),
                newCategoryTariffTable.getNewWaterTariffTableRangeList());
        return insertedWaterTariffTable.getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    private boolean softDeleteWaterTariffTableByClientCategoryId(int clientCategoryId, LocalDateTime endTimeUtc) {
        var oldCategoryTariffTable = waterTariffTableRepository
                .findByClientCategoryIdAndEndTimeUtcIsNull(clientCategoryId);
        if (oldCategoryTariffTable == null) {
            // TODO: Log warning
            return false;
        }
        oldCategoryTariffTable.setEndTimeUtc(endTimeUtc);
        waterTariffTableRepository.save(oldCategoryTariffTable);
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    private void createWaterTariffTableRanges(long waterTariffTableId,
                                   List<NewWaterTariffTableRange> newTariffTableRangeList) {
        for (int i = 0; i < newTariffTableRangeList.size(); i++) {
            var newTariffTableRanges = newTariffTableRangeList.get(i);
            var rangeStart = newTariffTableRanges.getRangeStartInM3();
            var rangeEnd = newTariffTableRanges.getRangeEndInM3();
            var price = newTariffTableRanges.getPricePerM3InCents();
            var waterTariffTableRange = new WaterTariffTableRange(waterTariffTableId,
                rangeStart, rangeEnd, price);
            waterTariffTableRangeRepository.save(waterTariffTableRange);
        }
    }

    @Override
    public List<ClientCategoryWaterTariffTable> getAllClientCategoryWaterTariffTables() {
        var categoryTariffTableList = new ArrayList<ClientCategoryWaterTariffTable>();
        var clientCategoryIterator = clientCategoryRepository.findAll().iterator();
        while (clientCategoryIterator.hasNext()) {
            var clientCategory = clientCategoryIterator.next();
            var waterTariffTable =
                waterTariffTableRepository.findByClientCategoryIdAndEndTimeUtcIsNull(
                    clientCategory.getId());
            if (waterTariffTable == null) {
                // TODO: Log warning
                continue;
            }
            var tariffTableRangeList = waterTariffTableRangeRepository
                .findByWaterTariffTableIdOrderByRangeStartInM3AscRangeEndInM3Asc(
                    waterTariffTable.getId());
            if (tariffTableRangeList.isEmpty()) {
                // TODO: Log warning
                continue;
            }
            var categoryTariffTable = new ClientCategoryWaterTariffTable(waterTariffTable.getId(),
                clientCategory, tariffTableRangeList);
            categoryTariffTableList.add(categoryTariffTable);
        }
        return categoryTariffTableList;
    }

    @Override
    public void softDeleteWaterTariffTable(long id) {
        Optional<WaterTariffTable> optionalWaterTariffTable = waterTariffTableRepository.findById(id);
        if (optionalWaterTariffTable.isEmpty()) {
            throw new NoSuchElementException("No water tariff table exists for " + id);
        }
        var waterTariffTable = optionalWaterTariffTable.get();
        if (waterTariffTable.getEndTimeUtc() == null) {
            LocalDateTime nowUtc = LocalDateTime.now(ZoneOffset.UTC);
            waterTariffTable.setEndTimeUtc(nowUtc);
        }
        waterTariffTableRepository.save(waterTariffTable);
    }

    @Override
    public CalculatedWaterTariff calculateWaterTariff(String clientCategoryName, int consumedVolumeInM3) {
        if (clientCategoryName.isBlank()) {
            throw new IllegalArgumentException("clientCategoryName must not be blank");
        }
        if (consumedVolumeInM3 < 0) {
            throw new IllegalArgumentException("consumedVolumeInM3 must be non-negative");
        }
        var clientCategory = clientCategoryRepository.findByName(clientCategoryName);
        if (clientCategory == null) {
            throw new NoSuchElementException("Couldn't find category for name \"" + clientCategoryName + "\"");
        }
        var waterTariffTable = waterTariffTableRepository.findByClientCategoryIdAndEndTimeUtcIsNull(
            clientCategory.getId());
        if (waterTariffTable == null) {
            throw new NoSuchElementException("Couldn't find tariff table for category " + clientCategory.getId());
        }
        var waterTariffTableRangeList =
            waterTariffTableRangeRepository.findByWaterTariffTableIdOrderByRangeStartInM3AscRangeEndInM3Asc(
                waterTariffTable.getId());
        if (waterTariffTableRangeList.isEmpty()) {
            throw new NoSuchElementException("Couldn't find any tariff table range for tariff table "
                + waterTariffTable.getId());
        }
        return waterTariffCalculator.calculateWaterTariffForRanges(waterTariffTableRangeList,
            consumedVolumeInM3);
    }
}
