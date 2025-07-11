package com.example.evms.infrastructure;

import com.example.evms.domain.Connector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface ConnectorRepository extends JpaRepository<Connector, Long> {
} 