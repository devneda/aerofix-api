package com.aerofix.api.exception;

public class PiezaNotFoundException extends RuntimeException {
    public PiezaNotFoundException(String message) {
        super(message);
    }
    public PiezaNotFoundException() {
        super("Pieza no encontrada");
    }
}