package com.example.evms.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.util.regex.Pattern;

@Embeddable
public class EVSEId {
    @NotNull
    private String value;

    private static final Pattern PATTERN = Pattern.compile("^[A-Z]{2}\\*[A-Z0-9]{3}\\*.{1,30}$");

    public EVSEId() {}
    public EVSEId(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid EVSE ID format. It must be <CountryCode>*<PartyID>*<LocalEVSEID>.");
        }
        this.value = value;
    }
    public static boolean isValid(String value) {
        return value != null && PATTERN.matcher(value).matches();
    }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
} 