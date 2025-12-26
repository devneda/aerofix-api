package com.aerofix.api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AvionDTO {
    private String matricula;
    private String modelo;
    private int capacidadPasajeros;
    private float horasVuelo;
    private boolean enServicio;
    private LocalDate fechaFabricacion;

    // Campos calculados (informativos)
    private int totalMantenimientos;
    private int totalVuelos;
}