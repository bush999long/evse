package com.example.evms.application;

import com.example.evms.domain.event.EVSEStatusChangedEvent;
import com.example.evms.domain.EVSEStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EVSEEventPublisher {
    @Autowired
    private ApplicationEventPublisher publisher;

    public void publishStatusChanged(Long evseId, EVSEStatus oldStatus, EVSEStatus newStatus) {
        publisher.publishEvent(new EVSEStatusChangedEvent(this, evseId, oldStatus, newStatus));
    }
} 