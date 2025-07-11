package com.example.evms.interfaces.dto;

import com.example.evms.domain.EVSE;
import java.time.OffsetDateTime;
import java.util.List;

public class LocationWithEVSEsDTO {
    private Long id;
    private String name;
    private String address;
    private String coordinates;
    private String businessHours;
    private OffsetDateTime lastUpdated;
    private List<EVSE> evses;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCoordinates() { return coordinates; }
    public void setCoordinates(String coordinates) { this.coordinates = coordinates; }
    public String getBusinessHours() { return businessHours; }
    public void setBusinessHours(String businessHours) { this.businessHours = businessHours; }
    public OffsetDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(OffsetDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    public List<EVSE> getEvses() { return evses; }
    public void setEvses(List<EVSE> evses) { this.evses = evses; }
} 