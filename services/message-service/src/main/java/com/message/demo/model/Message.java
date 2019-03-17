package com.message.demo.model;

import com.message.demo.jpa.PropertiesConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "messages")
@EntityListeners(AuditingEntityListener.class)
public class Message extends BaseObject{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String message;

    @Column(columnDefinition = ColumnDefinitions.PROPERTIES)
    @Convert(converter = PropertiesConverter.class)
    private Map<String, String> properties = new HashMap<>();

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }


    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public static final  List<String> validFields = Arrays.asList(new String[]{"id", "message"});

}
