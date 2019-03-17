package com.message.demo.test.component.common;

public class SerializationException extends RuntimeException {

    SerializationException(Exception e) {
        super(e);
    }
}