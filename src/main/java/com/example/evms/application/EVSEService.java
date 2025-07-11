package com.example.evms.application;

import com.example.evms.domain.*;
import com.example.evms.infrastructure.EVSERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class EVSEService {
    @Autowired
    private EVSERepository evseRepository;
    @Autowired
    private EVSEEventPublisher eventPublisher;

    @Transactional
    public EVSE addEVSE(EVSE evse) {
        if (!EVSEId.isValid(evse.getEvseId().getValue())) {
            throw new IllegalArgumentException("Invalid EVSE ID format");
        }
        evse.setStatus(EVSEStatus.AVAILABLE);
        evse.setLastUpdated(OffsetDateTime.now());
        return evseRepository.save(evse);
    }

    @Transactional
    public EVSE changeStatus(EVSE newEvse) {
        EVSE oldEvse = evseRepository.findById(newEvse.getId()).orElseThrow(() -> new RuntimeException("EVSE not found"));
        if (!EVSEStatusTransition.isValidTransition(oldEvse.getStatus(), newEvse.getStatus())) {
            throw new IllegalStateException("Invalid status transition");
        }

        newEvse.setLastUpdated(OffsetDateTime.now());
        EVSE saved = evseRepository.save(newEvse);
        eventPublisher.publishStatusChanged(newEvse.getId(), oldEvse.getStatus(), newEvse.getStatus());
        return saved;
    }

    public EVSE findById(Long id) {
        return evseRepository.findById(id).orElseThrow(() -> new RuntimeException("EVSE not found"));
    }
} 