package com.example.evms.application;

import com.example.evms.domain.Connector;
import com.example.evms.infrastructure.ConnectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConnectorService {
    @Autowired
    private ConnectorRepository connectorRepository;

    @Transactional
    public Connector addConnector(Connector connector) {
        return connectorRepository.save(connector);
    }
}