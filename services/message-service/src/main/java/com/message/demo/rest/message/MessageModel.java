package com.message.demo.rest.message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessageModel {

    private Long id;

    private String message;

    private Date createdDate;

    private Date modifiedDate;

    private Map<String, String> properties = new HashMap<>();

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(final Date modifiedDate) {
        this.modifiedDate = modifiedDate;
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
        for (Map.Entry<String, String> property : properties.entrySet()) {
            this.properties.put(property.getKey(), property.getValue());
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
