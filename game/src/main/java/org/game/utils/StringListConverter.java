package org.game.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = false)
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting list to JSON", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to list", e);
        }
    }
}

