package com.labym.flood.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.Map;

public class MapToJsonConverter implements AttributeConverter<Map<String,Object>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<Map<String,Object>> typeReference = new TypeReference() {};
    @Override
    public String convertToDatabaseColumn(Map<String,Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new PersistenceException("map convert to String failed!");
        }
    }

    @Override
    public Map<String,Object> convertToEntityAttribute(String data) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        try {
            return objectMapper.readValue(data, typeReference);
        } catch (IOException e) {
            throw new PersistenceException(String.format("read value as {},failed", typeReference));
        }
    }
}
