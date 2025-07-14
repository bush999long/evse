package com.example.evms.domain;

import com.example.evms.application.LocationService;
import com.example.evms.infrastructure.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocationServiceTest {
    @Mock
    private LocationRepository locationRepository;
    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLocation() {
        Location location = new Location();
        location.setName("Test Site");
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        Location saved = locationService.createLocation(location);
        assertEquals("Test Site", saved.getName());
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testUpdateLocation() {
        Location location = new Location();
        // id can only be set by reflection because there is no setId method
        java.lang.reflect.Field idField;
        try {
            idField = Location.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(location, 1L);
        } catch (Exception e) {
            fail("Failed to set id field: " + e.getMessage());
        }
        location.setName("Old Name");
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        location.setName("New Name");
        Location updated = locationService.updateLocation(location);
        assertEquals("New Name", updated.getName());
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testFindById() {
        Location location = new Location();
        try {
            java.lang.reflect.Field idField = Location.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(location, 2L);
        } catch (Exception e) {
            fail("Failed to set id field: " + e.getMessage());
        }
        when(locationRepository.findById(2L)).thenReturn(Optional.of(location));
        Optional<Location> found = locationService.findById(2L);
        assertTrue(found.isPresent());
        assertEquals(2L, found.get().getId());
    }
} 