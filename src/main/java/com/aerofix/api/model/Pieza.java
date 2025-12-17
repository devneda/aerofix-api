package com.aerofix.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class Pieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String referencia; // String ID

    @NotBlank
    private String nombre;

    @Min(0)
    private int stock; // int

    @Positive
    private float precioUnitario; // float

    private boolean esCritica; // boolean

    private LocalDate fechaUltimaRevision; // fecha
}