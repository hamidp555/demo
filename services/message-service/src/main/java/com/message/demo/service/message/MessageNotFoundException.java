package com.message.demo.service.message;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends RuntimeException {
    MessageNotFoundException(long id) {
        super(String.format("message with id %s not found", id));
    }
}
