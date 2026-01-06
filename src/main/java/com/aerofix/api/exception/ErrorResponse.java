package com.aerofix.api.exception;

import lombok.Data;
import java.util.Map;

@Data
public class ErrorResponse {
    private String message;
    private Map<String, String> errors;

    private ErrorResponse(String message) {
        this.message = message;
    }

    private ErrorResponse(Map<String, String> errors) {
        this.errors = errors;
    }

    public static ErrorResponse generalError(String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse validationError(Map<String, String> errors) {
        return new ErrorResponse(errors);
    }
}