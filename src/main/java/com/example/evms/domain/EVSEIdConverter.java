package com.example.evms.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EVSEIdConverter implements AttributeConverter<EVSEId, String> {
    @Override
    public String convertToDatabaseColumn(EVSEId attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public EVSEId convertToEntityAttribute(String dbData) {
        return dbData != null ? new EVSEId(dbData) : null;
    }
}