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
    @Column(unique = true, columnDefinition = "VARCHAR(255)")
    private String licenciaId;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(columnDefinition = "VARCHAR(50)") // Forzamos texto
    private String nombre;

    @Min(0) @Max(5)
    private int nivelExperiencia;

    @Positive
    private float salarioHora;

    private boolean disponible;

    @Temporal(TemporalType.DATE)
    private Date fechaContratacion;

    @OneToMany(mappedBy = "mecanico")
    private List<Mantenimiento> mantenimientosAsignados;
}