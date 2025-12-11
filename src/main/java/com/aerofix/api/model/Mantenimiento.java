package com.aerofix.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Mantenimiento {

    @Id
    @NotBlank
    private String codigoOrden; // String ID manual

    @Size(max = 500)
    private String descripcion;

    @Min(1)
    private int tiempoEstimadoHoras; // int

    @PositiveOrZero
    private float costeTotal; // float

    private boolean finalizado; // boolean

    @NotNull
    private LocalDateTime fechaEntrada; // fecha

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "avion_matricula", nullable = false)
    private Avion avion;

    @ManyToOne
    @JoinColumn(name = "mecanico_id")
    private Mecanico mecanico;
}