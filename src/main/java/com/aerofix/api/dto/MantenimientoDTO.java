package com.aerofix.api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MantenimientoDTO {
    private String codigoOrden;
    private String descripcion;
    private int tiempoEstimadoHoras;
    private float costeTotal;
    private boolean finalizado;
    private LocalDateTime fechaEntrada;
    private String matriculaAvion;
}