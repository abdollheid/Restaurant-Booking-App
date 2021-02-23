package com.Assignment.Restaurant.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"message"})
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<ErrorField> errors;

    public ApiError(HttpStatus status, String message, List<ErrorField> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, ErrorField error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorField> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorField> errors) {
        this.errors = errors;
    }
}
