package com.example.evms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private String coordinates;
    private String businessHours;
    // @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    // private Set<EVSE> evses = new HashSet<>();
    @NotNull
    private java.time.OffsetDateTime lastUpdated;
    public Location() {}
    public Location(String name, String address, String coordinates, String businessHours) {
        this.name = name;
        this.address = address;
        this.coordinates = coordinates;
        this.businessHours = businessHours;
        this.lastUpdated = java.time.OffsetDateTime.now();
    }
    public Long getId() {
        return id;
    }
    public void setLastUpdated(java.time.OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public java.time.OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
    public String getBusinessHours() {
        return businessHours;
    }
    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }
    // getter/setter 省略
} 