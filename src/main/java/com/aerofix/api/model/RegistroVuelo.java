package com.aerofix.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name= "registroVuelo")
public class RegistroVuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotBlank(message = "El código de vuelo es obligatorio")
    private String codigoVuelo;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotBlank
    private String origenDestino;

    @Column
    @Positive
    private Integer distanciaKm; // Wrapper

    @Column
    @Positive
    private Float combustibleConsumido; // Wrapper

    @Column
    private Boolean incidenciasReportadas; // Wrapper

    @Column
    @PastOrPresent
    private LocalDate fechaVuelo;

    @ManyToOne
    @JoinColumn(name = "avion_matricula", nullable = false)
    private Avion avion;
}