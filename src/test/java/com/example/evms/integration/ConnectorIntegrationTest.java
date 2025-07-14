package com.example.evms.integration;

import com.example.evms.domain.Connector;
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
public class ConnectorIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private EVSERepository evseRepository;
    @Autowired
    private ConnectorRepository connectorRepository;

    private Long evseId;

    @BeforeEach
    void setupEVSE() {
        // Create Location
        Location location = new Location();
        location.setName("Connector Test Site");
        location.setAddress("Test Address");
        location.setCoordinates("2,2");
        location.setBusinessHours("08:00-20:00");
        ResponseEntity<Location> locResp = restTemplate.postForEntity("/api/locations", location, Location.class);
        Location loc = locResp.getBody();
        assertNotNull(loc);
        Long locationId = loc.getId();

        // Create EVSE
        EVSE evse = new EVSE(new EVSEId("CN*ABC*EVSE654321"), EVSEStatus.AVAILABLE, locationId);
        ResponseEntity<EVSE> evseResp = restTemplate.postForEntity("/api/evses", evse, EVSE.class);
        EVSE evseResult = evseResp.getBody();
        assertNotNull(evseResult);
        evseId = evseResult.getId();
    }

    @AfterEach
    void cleanUp() {
        connectorRepository.deleteAll();
        evseRepository.deleteAll();
        locationRepository.deleteAll();
    }

    @Test
    void addConnector() {
        Connector connector = new Connector();
        connector.setStandard("CCS2");
        connector.setPowerLevel("120");
        connector.setVoltage(400);
        connector.setEvseId(evseId);

        ResponseEntity<Connector> createResp = restTemplate.postForEntity("/api/connectors", connector, Connector.class);
        assertEquals(HttpStatus.CREATED, createResp.getStatusCode());
        Connector result = createResp.getBody();
        assertNotNull(result);
        assertEquals("CCS2", result.getStandard());
    }
} 