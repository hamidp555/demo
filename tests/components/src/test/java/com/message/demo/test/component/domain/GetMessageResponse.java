package com.message.demo.test.component.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class GetMessageResponse {
    private Long id;

    private String message;

    @JsonIgnore
    private String createdDate;

    @JsonIgnore
    private String modifiedDate;

    private Map<String, String> properties = new HashMap<>();

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public void setProperties(final Map<String, String> properties) {
        this.properties = properties;
    }

    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(final String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(final String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
