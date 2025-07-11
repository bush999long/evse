package com.example.evms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Connector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String standard;
    @NotNull
    private String powerLevel;
    @NotNull
    private Integer voltage;
    @NotNull
    private Long evseId;

    public Connector() {}
    public Connector(String standard, String powerLevel, Integer voltage, Long evseId) {
        this.standard = standard;
        this.powerLevel = powerLevel;
        this.voltage = voltage;
        this.evseId = evseId;
    }

    // getter/setter methods
    public String getStandard() { return standard; }
    public void setStandard(String standard) { this.standard = standard; }
    public String getPowerLevel() { return powerLevel; }
    public void setPowerLevel(String powerLevel) { this.powerLevel = powerLevel; }
    public Integer getVoltage() { return voltage; }
    public void setVoltage(Integer voltage) { this.voltage = voltage; }
    public Long getEvseId() { return evseId; }
    public void setEvseId(Long evseId) { this.evseId = evseId; }
} 