package br.com.hraa.watertariffservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity(name = "water_tariff_table")
public class WaterTariffTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int clientCategoryId;

    @Column(nullable = false)
    private LocalDateTime startTimeUtc;

    @Column
    private LocalDateTime endTimeUtc;

    protected WaterTariffTable() { }

    public WaterTariffTable(Long id, int clientCategoryId, LocalDateTime startTimeUtc,
                            LocalDateTime endTimeUtc) {
        this.id = id;
        this.clientCategoryId = clientCategoryId;
        this.startTimeUtc = startTimeUtc;
        this.endTimeUtc = endTimeUtc;
    }

    public WaterTariffTable(int clientCategoryId, LocalDateTime startTimeUtc,
                            LocalDateTime endTimeUtc) {
        this.clientCategoryId = clientCategoryId;
        this.startTimeUtc = startTimeUtc;
        this.endTimeUtc = endTimeUtc;
    }

    public Long getId() {
        return id;
    }

    public int getClientCategoryId() {
        return clientCategoryId;
    }

    public LocalDateTime getStartTimeUtc() {
        return startTimeUtc;
    }

    public LocalDateTime getEndTimeUtc() {
        return endTimeUtc;
    }

    public void setEndTimeUtc(LocalDateTime endTimeUtc) {
        this.endTimeUtc = endTimeUtc;
    }
}
