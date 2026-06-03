package com.aerofix.api.dto;

import lombok.Data;
import java.util.Date;

@Data
public class MecanicoDTO {
    private Long id;
    private String licenciaId;
    private String nombre;
    private int nivelExperiencia;
    private float salarioHora;
    private boolean disponible;
    private Date fechaContratacion;
    private Double latitud;
    private Double longitud;
    private String fotoUrl;

    // Campo informativo
    private int totalMantenimientosAsignados;
}