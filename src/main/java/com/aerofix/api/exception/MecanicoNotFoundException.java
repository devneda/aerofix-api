package com.aerofix.api.exception;

public class MecanicoNotFoundException extends RuntimeException {
    public MecanicoNotFoundException(String message) {
        super(message);
    }
    public MecanicoNotFoundException() {
        super("Mecánico no encontrado");
    }
}