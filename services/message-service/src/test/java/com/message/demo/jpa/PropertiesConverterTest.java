package com.message.demo.jpa;

import org.junit.Test;

import javax.persistence.AttributeConverter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesConverterTest {

    private final AttributeConverter<Map<String, String>, String> sut = new PropertiesConverter();

    @Test
    public void convertToDatabaseColumn() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "b");

        assertThat(sut.convertToDatabaseColumn(map))
                .isEqualTo("{\"a\":\"b\"}");
    }

    @Test
    public void convertToEntityAttribute() {
        Map<String, String> map = sut.convertToEntityAttribute("{\"a\":\"b\"}");

        assertThat(map.get("a")).isEqualTo("b");
        assertThat(map.size()).isEqualTo(1);
    }
}