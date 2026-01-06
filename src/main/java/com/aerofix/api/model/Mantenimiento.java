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
    @Column(columnDefinition = "VARCHAR(255)")
    private String codigoOrden; // String ID manual

    @Column(columnDefinition = "VARCHAR(500)")
    @Size(max = 500)
    private String descripcion;

    @Column
    @Min(value = 1, message = "El tiempo estimado debe ser mínimo 1 hora")
    private Integer tiempoEstimadoHoras; // int

    @Column
    @PositiveOrZero
    private Float costeTotal; // float

    @Column
    private Boolean finalizado; // boolean

    @Column
    @NotNull
    private LocalDateTime fechaEntrada; // fecha

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "avion_matricula", nullable = false)
    private Avion avion;

    @ManyToOne
    @JoinColumn(name = "mecanico_id")
    private Mecanico mecanico;

    @ManyToMany
    @JoinTable(
            name = "mantenimiento_piezas",
            joinColumns = @JoinColumn(name = "mantenimiento_id"),
            inverseJoinColumns = @JoinColumn(name = "pieza_id")
    )
    private java.util.List<Pieza> piezasEmpleadas;
}