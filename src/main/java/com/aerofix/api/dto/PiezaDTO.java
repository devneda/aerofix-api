package com.aerofix.api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PiezaDTO {
    private Long id;
    private String referencia;
    private String nombre;
    private int stock;
    private float precioUnitario;
    private boolean esCritica;
    private LocalDate fechaUltimaRevision;
    private int piezasEmpleadas;
}