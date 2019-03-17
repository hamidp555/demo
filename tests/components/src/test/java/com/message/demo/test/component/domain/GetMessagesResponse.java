package com.message.demo.test.component.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMessagesResponse {

    List<Message> content = new ArrayList<>();

    public List<Message> getContent() {
        return this.content;
    }

    public void setContent(final List<Message> content) {
        this.content = content;
    }
}
