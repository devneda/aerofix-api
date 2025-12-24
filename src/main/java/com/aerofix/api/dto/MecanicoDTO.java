package com.aerofix.api.dto;

import lombok.Data;
import java.util.Date;

@Data
public class MecanicoDTO {
    private String licenciaId;
    private String nombre;
    private int nivelExperiencia;
    private float salarioHora;
    private boolean disponible;
    private Date fechaContratacion;

    // Campo informativo
    private int totalMantenimientosAsignados;
}