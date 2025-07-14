package com.example.evms.integration;

import com.example.evms.domain.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;
import com.example.evms.infrastructure.LocationRepository;
import com.example.evms.infrastructure.EVSERepository;
import com.example.evms.infrastructure.ConnectorRepository;
import org.junit.jupiter.api.Tag;

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private EVSERepository evseRepository;
    @Autowired
    private ConnectorRepository connectorRepository;

    @Test
    void create() {
        Location location = new Location();
        location.setName("Test Site");
        location.setAddress("Test Address");
        location.setCoordinates("0,0");
        location.setBusinessHours("08:00-20:00");

        ResponseEntity<Location> createResp = restTemplate.postForEntity("/api/locations", location, Location.class);
        assertEquals(HttpStatus.CREATED, createResp.getStatusCode());
        Location created = createResp.getBody();
        assertNotNull(created);
    }

    @Test
    void update() {
        // 先创建一个Location
        Location location = new Location();
        location.setName("Test Site");
        location.setAddress("Test Address");
        location.setCoordinates("0,0");
        location.setBusinessHours("08:00-20:00");
        ResponseEntity<Location> createResp = restTemplate.postForEntity("/api/locations", location, Location.class);
        Location created = createResp.getBody();
        assertNotNull(created);
        Long id = created.getId();

        // 构造更新内容
        location.setName("Updated Site");
        location.setAddress("Updated Address");
        location.setCoordinates("1,1");
        location.setBusinessHours("09:00-21:00");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Location> entity = new HttpEntity<>(location, headers);
        ResponseEntity<Location> putResp = restTemplate.exchange("/api/locations/", HttpMethod.PUT, entity, Location.class);
        assertEquals(HttpStatus.OK, putResp.getStatusCode());
        Location updated = putResp.getBody();
        assertNotNull(updated);
        assertEquals("Updated Site", updated.getName());
        assertEquals("Updated Address", updated.getAddress());
        assertEquals("1,1", updated.getCoordinates());
        assertEquals("09:00-21:00", updated.getBusinessHours());
    }

    @AfterEach
    void cleanUp() {
        connectorRepository.deleteAll();
        evseRepository.deleteAll();
        locationRepository.deleteAll();
    }
} 