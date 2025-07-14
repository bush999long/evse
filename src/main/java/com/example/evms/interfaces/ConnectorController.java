package com.example.evms.interfaces;

import com.example.evms.application.ConnectorService;
import com.example.evms.domain.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/connectors")
public class ConnectorController {
    @Autowired
    private ConnectorService connectorService;

    @PostMapping
    public ResponseEntity<Connector> addConnector(@RequestBody Connector connector) {
        Connector saved = connectorService.addConnector(connector);
        return new ResponseEntity<>(saved, org.springframework.http.HttpStatus.CREATED);
    }
} 