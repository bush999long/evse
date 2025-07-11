package com.example.evms.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EVSEId Format Validation Test")
public class EVSEIdTest {

    @Test
    @DisplayName("Valid EVSE ID format")
    void validEVSEId() {
        // Test valid EVSE ID formats
        assertTrue(EVSEId.isValid("CN*ABC*EVSE123456"));
        assertTrue(EVSEId.isValid("US*XYZ*EVSE0001"));
        assertTrue(EVSEId.isValid("NL*DEF*CHARGER001"));
        assertTrue(EVSEId.isValid("DE*GHI*STATION123"));
        assertTrue(EVSEId.isValid("FR*JKL*POWER456"));
    }

    @Test
    @DisplayName("Invalid EVSE ID format - Country code error")
    void invalidCountryCode() {
        // Country code is not 2 uppercase letters
        assertTrue(EVSEId.isValid("C*ABC*EVSE123456")); // Only 1 letter
        assertFalse(EVSEId.isValid("CN1*ABC*EVSE123456")); // Contains number
    }

    @Test
    @DisplayName("Invalid EVSE ID format - PartyID error")
    void invalidPartyID() {
        // PartyID is not 3 alphanumeric
        assertFalse(EVSEId.isValid("CN*AB*EVSE123456")); // Only 2 chars
        assertFalse(EVSEId.isValid("CN*ABCD*EVSE123456")); // 4 chars
        assertFalse(EVSEId.isValid("CN*abc*EVSE123456")); // Lowercase
    }

    @Test
    @DisplayName("Invalid EVSE ID format - LocalEVSEID error")
    void invalidLocalEVSEID() {
        // LocalEVSEID is empty or too long
        assertFalse(EVSEId.isValid("CN*ABC*")); // Empty
        assertFalse(EVSEId.isValid("CN*ABC*EVSE1234567890123456789012345678901")); // Too long (31 chars)
        assertFalse(EVSEId.isValid("CN*ABC*")); // Empty
    }

    @Test
    @DisplayName("Invalid EVSE ID format - Separator error")
    void invalidSeparator() {
        // Separator is not *
        assertFalse(EVSEId.isValid("CN-ABC-EVSE123456")); // Uses -
        assertFalse(EVSEId.isValid("CN_ABC_EVSE123456")); // Uses _
        assertFalse(EVSEId.isValid("CN.ABC.EVSE123456")); // Uses .
    }

    @Test
    @DisplayName("Invalid EVSE ID format - Overall format error")
    void invalidOverallFormat() {
        // Overall format error
        assertFalse(EVSEId.isValid("")); // Empty string
        assertFalse(EVSEId.isValid(null)); // null
        assertFalse(EVSEId.isValid("CN*ABC")); // Only two parts
    }

    @Test
    @DisplayName("EVSEId constructor test")
    void testConstructor() {
        // Test valid ID constructor
        EVSEId validId = new EVSEId("CN*ABC*EVSE123456");
        assertEquals("CN*ABC*EVSE123456", validId.getValue());

        // Test invalid ID constructor should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            new EVSEId("INVALID_FORMAT");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new EVSEId("CN*AB*EVSE123456");
        });
    }

    @Test
    @DisplayName("Boundary value test")
    void boundaryValueTest() {
        // Test max length (30 chars)
        String maxLengthLocalId = "123456789012345678901234567890";
        assertTrue(EVSEId.isValid("CN*ABC*" + maxLengthLocalId));

        // Test min length (1 char)
        assertTrue(EVSEId.isValid("CN*ABC*1"));

        // Test special chars (allowed in LocalEVSEID)
        assertTrue(EVSEId.isValid("CN*ABC*EVSE-123_456"));
        assertTrue(EVSEId.isValid("CN*ABC*EVSE.123"));
    }

    @Test
    @DisplayName("Different country codes test")
    void differentCountryCodes() {
        // Test various valid country codes
        assertTrue(EVSEId.isValid("US*ABC*EVSE123456"));
        assertTrue(EVSEId.isValid("CN*ABC*EVSE123456"));
        assertTrue(EVSEId.isValid("DE*ABC*EVSE123456"));
        assertTrue(EVSEId.isValid("FR*ABC*EVSE123456"));
        assertTrue(EVSEId.isValid("UK*ABC*EVSE123456"));
        assertTrue(EVSEId.isValid("JP*ABC*EVSE123456"));
        assertTrue(EVSEId.isValid("KR*ABC*EVSE123456"));
    }

    @Test
    @DisplayName("Invalid EVSE ID format - PartyID contains non-alphanumeric")
    void invalidPartyIDNonAlphanumeric() {
        assertFalse(EVSEId.isValid("CN*AB$*EVSE123456")); // PartyID contains special character
        assertFalse(EVSEId.isValid("CN*AB *EVSE123456")); // PartyID contains space
    }

    @Test
    @DisplayName("Valid EVSE ID format - LocalEVSEID with special characters")
    void validLocalEVSEIDSpecialChars() {
        assertTrue(EVSEId.isValid("CN*ABC*EVSE-123_456.789"));
    }
} 