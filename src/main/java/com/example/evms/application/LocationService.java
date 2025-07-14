package com.example.evms.application;

import com.example.evms.domain.Location;
import com.example.evms.domain.EVSE;
import com.example.evms.infrastructure.LocationRepository;
import com.example.evms.infrastructure.EVSERepository;
import com.example.evms.interfaces.dto.LocationWithEVSEsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private EVSERepository evseRepository;

    @Transactional
    public Location createLocation(Location location) {
        location.setLastUpdated(OffsetDateTime.now());
        return locationRepository.save(location);
    }

    @Transactional
    public Location updateLocation(Location updated) {
        Location location = locationRepository.findById(updated.getId()).orElseThrow(() -> new RuntimeException("Location doesnot exist"));
        location.setName(updated.getName());
        location.setAddress(updated.getAddress());
        location.setCoordinates(updated.getCoordinates());
        location.setBusinessHours(updated.getBusinessHours());
        location.setLastUpdated(OffsetDateTime.now());
        return locationRepository.save(location);
    }

    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    public org.springframework.data.domain.Page<LocationWithEVSEsDTO> findLocationsWithEVSEsByLastUpdated(java.time.OffsetDateTime lastUpdated, org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.domain.Page<Location> locations = locationRepository.findByLastUpdatedAfter(lastUpdated, pageable);
        return locations.map(location -> {
            List<EVSE> evses = evseRepository.findAll().stream()
                .filter(evse -> evse.getLocationId().equals(location.getId()))
                .collect(Collectors.toList());
            LocationWithEVSEsDTO dto = new LocationWithEVSEsDTO();
            dto.setId(location.getId());
            dto.setName(location.getName());
            dto.setAddress(location.getAddress());
            dto.setCoordinates(location.getCoordinates());
            dto.setBusinessHours(location.getBusinessHours());
            dto.setLastUpdated(location.getLastUpdated());
            dto.setEvses(evses);
            return dto;
        });
    }
} 