package com.example.evms.infrastructure;

import com.example.evms.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findByLastUpdatedAfter(OffsetDateTime lastUpdated, Pageable pageable);
} 