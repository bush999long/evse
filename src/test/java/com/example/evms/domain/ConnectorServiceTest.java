package com.example.evms.domain;

import com.example.evms.application.ConnectorService;
import com.example.evms.infrastructure.ConnectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConnectorServiceTest {
    @Mock
    private ConnectorRepository connectorRepository;
    @InjectMocks
    private ConnectorService connectorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddConnector() {
        Connector connector = new Connector();
        connector.setStandard("CCS2");
        connector.setPowerLevel("120");
        connector.setVoltage(400);
        when(connectorRepository.save(any(Connector.class))).thenReturn(connector);
        Connector saved = connectorService.addConnector(connector);
        assertEquals("CCS2", saved.getStandard());
        verify(connectorRepository, times(1)).save(connector);
    }
} 