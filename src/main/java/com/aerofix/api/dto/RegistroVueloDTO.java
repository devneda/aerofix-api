package com.aerofix.api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegistroVueloDTO {
    private Long id;
    private String codigoVuelo;
    private String origenDestino;
    private int distanciaKm;
    private float combustibleConsumido;
    private boolean incidenciasReportadas;
    private LocalDate fechaVuelo;
    private String matriculaAvion;
}