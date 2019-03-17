package com.message.demo.rest.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;


class ApiError {

    @JsonIgnore
    private Throwable throwable;

    private String message;

    ApiError(Throwable throwable) {
        this.throwable = throwable;
        this.message = throwable.getMessage();
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public void setThrowable(final Throwable throwable) {
        this.throwable = throwable;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
