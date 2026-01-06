package com.aerofix.api.exception;

public class AvionNotFoundException extends RuntimeException {
    public AvionNotFoundException(String message) {
        super(message);
    }

    public AvionNotFoundException() {
        super("Avion no encontrado");
    }
}