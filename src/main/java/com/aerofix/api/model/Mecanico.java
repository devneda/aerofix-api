package com.aerofix.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Mecanico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El ID de licencia es obligatorio")
    @Column(unique = true)
    private String licenciaId; // String identificativo

    @Column(columnDefinition = "VARCHAR(50)")
    @NotBlank
    @Size(min = 2, max = 50)
    private String nombre;

    @Column
    @Min(0) @Max(5)
    private int nivelExperiencia; // int

    @Column
    @Positive
    private float salarioHora; // float

    @Column
    private boolean disponible; // boolean

    @Column
    @Temporal(TemporalType.DATE)
    private Date fechaContratacion; // fecha (java.util.Date para variar)

    @OneToMany(mappedBy = "mecanico")
    private List<Mantenimiento> mantenimientosAsignados;
}