package com.aerofix.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class RegistroVuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String codigoVuelo; // String

    @NotBlank
    private String origenDestino;

    @Positive
    private int distanciaKm; // int

    @Positive
    private float combustibleConsumido; // float

    private boolean incidenciasReportadas; // boolean

    @PastOrPresent
    private LocalDate fechaVuelo; // fecha

    @ManyToOne
    @JoinColumn(name = "avion_matricula", nullable = false)
    private Avion avion;
}