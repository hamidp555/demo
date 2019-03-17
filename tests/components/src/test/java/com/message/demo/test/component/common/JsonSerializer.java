package com.message.demo.test.component.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class JsonSerializer {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String serialize(T object) {
        requireNonNull(object);
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        requireNonNull(json);
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

}
