package com.message.demo.rest.exception;

import com.message.demo.service.message.MessageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleNotFoundException(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(ex);
        if (ex instanceof MessageNotFoundException) {
            apiError = new ApiError(ex);
            return handleException(apiError, HttpStatus.NOT_FOUND);
        } else {
            apiError = new ApiError(ex);
            return handleException(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<ApiError> handleException(ApiError apiError, HttpStatus status) {
        logger.error(apiError.getThrowable());
        return new ResponseEntity<ApiError>(apiError, status);
    }

}
