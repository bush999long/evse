package com.example.evms.domain;

import com.example.evms.application.EVSEService;
import com.example.evms.infrastructure.EVSERepository;
import com.example.evms.application.EVSEEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EVSEServiceTest {
    @Mock
    private EVSERepository evseRepository;
    @Mock
    private EVSEEventPublisher eventPublisher;
    @InjectMocks
    private EVSEService evseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEVSE_valid() {
        EVSEId evseId = new EVSEId("CN*ABC*EVSE123456");
        EVSE evse = new EVSE(evseId, EVSEStatus.AVAILABLE, 1L);
        when(evseRepository.save(any(EVSE.class))).thenReturn(evse);
        EVSE saved = evseService.addEVSE(evse);
        assertEquals(evseId.getValue(), saved.getEvseId().getValue());
        verify(evseRepository, times(1)).save(evse);
    }

    @Test
    void testAddEVSE_invalidId() {
        assertThrows(IllegalArgumentException.class, () -> new EVSEId("INVALID"));
    }

    @Test
    void testChangeStatus_validTransition() {
        EVSEId evseId = new EVSEId("CN*ABC*EVSE123456");
        EVSE oldEvse = new EVSE(evseId, EVSEStatus.AVAILABLE, 1L);
        try {
            java.lang.reflect.Field idField = EVSE.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(oldEvse, 1L);
        } catch (Exception e) {
            fail("Failed to set id field: " + e.getMessage());
        }
        EVSE newEvse = new EVSE(evseId, EVSEStatus.BLOCKED, 1L);
        try {
            java.lang.reflect.Field idField = EVSE.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(newEvse, 1L);
        } catch (Exception e) {
            fail("Failed to set id field: " + e.getMessage());
        }
        when(evseRepository.findById(1L)).thenReturn(Optional.of(oldEvse));
        when(evseRepository.save(any(EVSE.class))).thenReturn(newEvse);
        EVSE result = evseService.changeStatus(newEvse);
        assertEquals(EVSEStatus.BLOCKED, result.getStatus());
        verify(eventPublisher, times(1)).publishStatusChanged(1L, EVSEStatus.AVAILABLE, EVSEStatus.BLOCKED);
    }

    @Test
    void testChangeStatus_invalidTransition() {
        EVSEId evseId = new EVSEId("CN*ABC*EVSE123456");
        EVSE oldEvse = new EVSE(evseId, EVSEStatus.AVAILABLE, 1L);
        try {
            java.lang.reflect.Field idField = EVSE.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(oldEvse, 1L);
        } catch (Exception e) {
            fail("Failed to set id field: " + e.getMessage());
        }
        EVSE newEvse = new EVSE(evseId, EVSEStatus.REMOVED, 1L);
        try {
            java.lang.reflect.Field idField = EVSE.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(newEvse, 1L);
        } catch (Exception e) {
            fail("Failed to set id field: " + e.getMessage());
        }
        when(evseRepository.findById(1L)).thenReturn(Optional.of(oldEvse));
        when(evseRepository.save(any(EVSE.class))).thenReturn(newEvse);
        // AVAILABLE -> REMOVED is valid, but test AVAILABLE -> INOPERATIVE -> REMOVED
        oldEvse.setStatus(EVSEStatus.INOPERATIVE);
        newEvse.setStatus(EVSEStatus.AVAILABLE); // Invalid rollback
        assertThrows(IllegalStateException.class, () -> evseService.changeStatus(newEvse));
    }

    @Test
    void testFindById() {
        EVSEId evseId = new EVSEId("CN*ABC*EVSE123456");
        EVSE evse = new EVSE(evseId, EVSEStatus.AVAILABLE, 1L);
        try {
            java.lang.reflect.Field idField = EVSE.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(evse, 2L);
        } catch (Exception e) {
            fail("Failed to set id field: " + e.getMessage());
        }
        when(evseRepository.findById(2L)).thenReturn(Optional.of(evse));
        EVSE found = evseService.findById(2L);
        assertEquals(2L, found.getId());
    }
} 