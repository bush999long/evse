package com.example.evms.interfaces;

import com.example.evms.application.LocationService;
import com.example.evms.domain.Location;
import com.example.evms.interfaces.dto.LocationWithEVSEsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> create(@RequestBody Location location) {
        return ResponseEntity.ok(locationService.createLocation(location));
    }

    @PatchMapping()
    public ResponseEntity<Location> Update(@RequestBody Location location) {
        return ResponseEntity.ok(locationService.updateLocation(location));
    }

    @GetMapping()
    public Page<LocationWithEVSEsDTO> queryLocationsWithEVSEsByLastUpdated(
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.OffsetDateTime lastUpdated,
            @RequestParam int page,
            @RequestParam int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return locationService.findLocationsWithEVSEsByLastUpdated(lastUpdated, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> get(@PathVariable Long id) {
        System.err.println("id: " + id);
        return ResponseEntity.ok(locationService.findById(id).orElse(null));
    }
} 