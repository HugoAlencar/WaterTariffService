package br.com.hraa.watertariffservice.repository;

import br.com.hraa.watertariffservice.model.WaterTariffTableRange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaterTariffTableRangeRepository extends JpaRepository<WaterTariffTableRange, Long> {
    List<WaterTariffTableRange> findByWaterTariffTableIdOrderByRangeStartInM3AscRangeEndInM3Asc(
        long waterTariffTableId);
}
