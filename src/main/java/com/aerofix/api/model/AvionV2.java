package com.aerofix.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity(name = "AvionV2")
@Table(name = "avion")
public class AvionV2 {

    @Id
    @NotBlank(message = "La matrícula es obligatoria")
    @Pattern(regexp = "^[A-Z]{2}-\\d{3,4}$", message = "Formato de matrícula inválido (Ej: EC-123)")
    private String matricula;

    private String modelo;
    private int capacidadPasajeros;
    private float horasVuelo;
    private boolean enServicio;
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFabricacion;

    // Nuevo campo para la V2
    @Column(name = "aerolinea")
    private String aerolinea;
}