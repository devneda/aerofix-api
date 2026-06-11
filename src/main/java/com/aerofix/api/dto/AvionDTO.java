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
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFabricacion;
    private Double latitud;
    private Double longitud;
    private String imagenUrl;

    // Campos calculados (informativos)
    private int totalMantenimientos;
    private int totalVuelos;
}