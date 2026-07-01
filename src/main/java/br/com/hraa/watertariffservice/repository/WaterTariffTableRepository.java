package br.com.hraa.watertariffservice.repository;

import br.com.hraa.watertariffservice.model.WaterTariffTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterTariffTableRepository extends JpaRepository<WaterTariffTable, Long> {
    WaterTariffTable findByClientCategoryIdAndEndTimeUtcIsNull(int clientCategoryId);
}
