package com.aerofix.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RegistroVueloDTO {

    @Schema(description = "Identificador único", example = "10")
    private Long id;

    @Schema(description = "Código del vuelo", example = "IB-3400")
    private String codigoVuelo;

    @Schema(description = "Ruta", example = "Madrid (MAD) - Londres (LHR)")
    private String origenDestino;

    @Schema(description = "Distancia recorrida", example = "1200")
    private int distanciaKm;

    @Schema(description = "Litros de combustible", example = "5000.5")
    private float combustibleConsumido;

    @Schema(description = "Hubo problemas", example = "false")
    private boolean incidenciasReportadas;

    @Schema(description = "Fecha del vuelo", example = "2025-05-20")
    private LocalDate fechaVuelo;

    @Schema(description = "Matrícula del avión asociado", example = "EC-1234")
    private String matriculaAvion;
}