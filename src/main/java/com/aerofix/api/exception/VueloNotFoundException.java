package com.aerofix.api.exception;

public class VueloNotFoundException extends RuntimeException {
    public VueloNotFoundException(String message) {
        super(message);
    }
    public VueloNotFoundException() {
        super("Vuelo no encontrado");
    }
}