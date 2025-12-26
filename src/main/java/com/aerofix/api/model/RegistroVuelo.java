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

    @Column(columnDefinition = "VARCHAR(255)")
    @NotBlank
    private String codigoVuelo; // String

    @Column(columnDefinition = "VARCHAR(255)")
    @NotBlank
    private String origenDestino;

    @Column
    @Positive
    private int distanciaKm; // int

    @Column
    @Positive
    private float combustibleConsumido; // float

    @Column
    private boolean incidenciasReportadas; // boolean

    @Column
    @PastOrPresent
    private LocalDate fechaVuelo; // fecha

    @ManyToOne
    @JoinColumn(name = "avion_matricula", nullable = false)
    private Avion avion;
}