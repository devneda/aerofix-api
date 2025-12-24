package com.aerofix.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Avion {

    @Id
    @NotBlank(message = "La matrícula es obligatoria") // Requisito: atributo obligatorio
    @Pattern(regexp = "^[A-Z]{2}-\\d{3,4}$", message = "Formato de matrícula inválido (Ej: EC-123)") // Requisito: validación formato
    private String matricula;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @Column
    @Min(value = 1, message = "La capacidad debe ser positiva")
    private int capacidadPasajeros; // int

    @Column
    @NotNull
    private float horasVuelo; // float

    @Column
    private boolean enServicio; // boolean

    @Column
    @PastOrPresent
    private LocalDate fechaFabricacion; // fecha

    // Relaciones
    @OneToMany(mappedBy = "avion", cascade = CascadeType.ALL)
    private List<Mantenimiento> mantenimientos;

    @OneToMany(mappedBy = "avion", cascade = CascadeType.ALL)
    private List<RegistroVuelo> vuelos;
}