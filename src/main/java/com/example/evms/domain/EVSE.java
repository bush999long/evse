package com.example.evms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class EVSE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "evse_id"))
    private EVSEId evseId;

    @Enumerated(EnumType.STRING)
    private EVSEStatus status;

    @NotNull
    private Long locationId;

    @NotNull
    private java.time.OffsetDateTime lastUpdated;

    public EVSE() {}
    public EVSE(EVSEId evseId, EVSEStatus status, Long locationId) {
        this.evseId = evseId;
        this.status = status;
        this.locationId = locationId;
        this.lastUpdated = java.time.OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public EVSEId getEvseId() {
        return evseId;
    }
    public void setEvseId(EVSEId evseId) {
        this.evseId = evseId;
    }
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
    public Long getLocationId() {
        return locationId;
    }
    public void setStatus(EVSEStatus status) {
        this.status = status;
    }
    public EVSEStatus getStatus() {
        return status;
    }
    public void setLastUpdated(java.time.OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public java.time.OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }
    // getter/setter 省略
} 