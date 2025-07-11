package com.example.evms.domain.event;

import com.example.evms.domain.EVSEStatus;
import org.springframework.context.ApplicationEvent;

public class EVSEStatusChangedEvent extends ApplicationEvent {
    private final Long evseId;
    private final EVSEStatus oldStatus;
    private final EVSEStatus newStatus;

    public EVSEStatusChangedEvent(Object source, Long evseId, EVSEStatus oldStatus, EVSEStatus newStatus) {
        super(source);
        this.evseId = evseId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }
    public Long getEvseId() { return evseId; }
    public EVSEStatus getOldStatus() { return oldStatus; }
    public EVSEStatus getNewStatus() { return newStatus; }
} 