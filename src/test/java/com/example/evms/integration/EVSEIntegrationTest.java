package com.example.evms.integration;

import com.example.evms.domain.EVSE;
import com.example.evms.domain.EVSEId;
import com.example.evms.domain.EVSEStatus;
import com.example.evms.domain.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import com.example.evms.infrastructure.LocationRepository;
import com.example.evms.infrastructure.EVSERepository;
import com.example.evms.infrastructure.ConnectorRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EVSEIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private EVSERepository evseRepository;
    @Autowired
    private ConnectorRepository connectorRepository;

    private Long locationId;

    @BeforeEach
    void setupLocation() {
        Location location = new Location();
        location.setName("EVSE Test Site");
        location.setAddress("Test Address");
        location.setCoordinates("1,1");
        location.setBusinessHours("08:00-20:00");
        ResponseEntity<Location> resp = restTemplate.postForEntity("/api/locations", location, Location.class);
        Location loc = resp.getBody();
        assertNotNull(loc);
        locationId = loc.getId();
    }

    @AfterEach
    void cleanUp() {
        connectorRepository.deleteAll();
        evseRepository.deleteAll();
        locationRepository.deleteAll();
    }

    @Test
    void addAndChangeStatusEVSE() {
        EVSE evse = new EVSE(new EVSEId("CN*ABC*EVSE123456"), EVSEStatus.AVAILABLE, locationId);

        ResponseEntity<EVSE> resp = restTemplate.postForEntity("/api/evses", evse, EVSE.class);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        EVSE update = resp.getBody();
        assertNotNull(update);

        update.setStatus(EVSEStatus.BLOCKED);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EVSE> entity = new HttpEntity<>(update, headers);

        ResponseEntity<EVSE> putResp = restTemplate.exchange("/api/evses", HttpMethod.PUT, entity, EVSE.class);
        assertEquals(HttpStatus.OK, putResp.getStatusCode());
        EVSE result = putResp.getBody();
        assertNotNull(result);
        assertEquals(EVSEStatus.BLOCKED, result.getStatus());
    }
} 