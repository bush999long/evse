package com.example.evms.infrastructure;

import com.example.evms.domain.EVSE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface EVSERepository extends JpaRepository<EVSE, Long> {
} 