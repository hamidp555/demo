package com.message.demo.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Converter
public class PropertiesConverter implements AttributeConverter<Map<String, String>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, String> data) {
        String value = null;
        if (data == null || data.isEmpty()) return value;
        try {
            value = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String data) {
        Map<String, String> properties = null;
        if (data == null || data.isEmpty()) return properties;
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
        };
        try {
            properties = mapper.readValue(data, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

}