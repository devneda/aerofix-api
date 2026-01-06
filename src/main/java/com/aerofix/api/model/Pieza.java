package com.aerofix.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Pieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La referencia es obligatoria")
    @Column(unique = true, columnDefinition = "VARCHAR(255)")
    private String referencia;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column
    @Min(0)
    private Integer stock; // Wrapper

    @Column
    @Positive
    private Float precioUnitario; // Wrapper

    @Column
    private Boolean esCritica; // Wrapper

    @Column
    private LocalDate fechaUltimaRevision;

    @ManyToMany(mappedBy = "piezasEmpleadas")
    private List<Mantenimiento> mantenimientosDondeSeUso;
}