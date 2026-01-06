package com.aerofix.api.exception;

public class MantenimientoNotFoundException extends RuntimeException {
    public MantenimientoNotFoundException(String message) {
        super(message);
    }

    public MantenimientoNotFoundException() {
        super("Mantenimiento no encontrado");
    }
}