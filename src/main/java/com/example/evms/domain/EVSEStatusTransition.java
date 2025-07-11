package com.example.evms.domain;

import java.util.EnumSet;
import java.util.Set;

public class EVSEStatusTransition {
    private static final Set<EVSEStatus> AVAILABLE = EnumSet.of(EVSEStatus.BLOCKED, EVSEStatus.INOPERATIVE, EVSEStatus.REMOVED);
    private static final Set<EVSEStatus> BLOCKED = EnumSet.of(EVSEStatus.AVAILABLE, EVSEStatus.REMOVED);
    private static final Set<EVSEStatus> INOPERATIVE = EnumSet.of(EVSEStatus.AVAILABLE, EVSEStatus.REMOVED);

    public static boolean isValidTransition(EVSEStatus from, EVSEStatus to) {
        if (from == null && to == EVSEStatus.AVAILABLE) return true;
        if (from == null) return false;
        switch (from) {
            case AVAILABLE: return AVAILABLE.contains(to);
            case BLOCKED: return BLOCKED.contains(to);
            case INOPERATIVE: return INOPERATIVE.contains(to);
            case REMOVED: return false;
            default: return false;
        }
    }
} 