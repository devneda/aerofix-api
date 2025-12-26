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
    @Column(unique = true, columnDefinition = "VARCHAR(255)")
    private String referencia; // String ID

    @Column(columnDefinition = "VARCHAR(255)")
    @NotBlank
    private String nombre;

    @Column
    @Min(0)
    private int stock; // int

    @Column
    @Positive
    private float precioUnitario; // float

    @Column
    private boolean esCritica; // boolean

    @Column
    private LocalDate fechaUltimaRevision; // fecha

    @ManyToMany(mappedBy = "piezasEmpleadas")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.util.List<Mantenimiento> mantenimientosDondeSeUso;
}