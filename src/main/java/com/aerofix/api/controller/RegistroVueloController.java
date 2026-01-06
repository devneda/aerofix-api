package com.aerofix.api.controller;

import com.aerofix.api.dto.RegistroVueloDTO;
import com.aerofix.api.exception.ErrorResponse;
import com.aerofix.api.exception.VueloNotFoundException;
import com.aerofix.api.model.RegistroVuelo;
import com.aerofix.api.service.RegistroVueloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vuelos")
@Tag(name = "Vuelos", description = "Operaciones de gestión de registros de vuelo")
public class RegistroVueloController {

    @Autowired
    private RegistroVueloService vueloService;

    @Operation(summary = "Obtener lista de vuelos", description = "Devuelve todos los vuelos registrados. Permite filtrar por destino, incidencias o código parcial.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vuelos recuperada con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistroVueloDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<RegistroVueloDTO>> obtenerTodos(
            @Parameter(description = "Filtra vuelos por ciudad de origen o destino")
            @RequestParam(required = false) String destino,

            @Parameter(description = "Filtra vuelos según si han reportado incidencias (true/false)")
            @RequestParam(required = false) Boolean incidencias,

            @Parameter(description = "Filtra por coincidencia parcial en el código de vuelo")
            @RequestParam(required = false) String codigo
    ) {
        return ResponseEntity.ok(vueloService.buscarVuelos(destino, incidencias, codigo));
    }

    @Operation(summary = "Obtener vuelo por ID", description = "Devuelve los detalles de un vuelo específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vuelo encontrado",
                    content = @Content(schema = @Schema(implementation = RegistroVueloDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vuelo no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RegistroVueloDTO> obtenerPorId(@Parameter(description = "ID numérico del vuelo") @PathVariable Long id) {
        return ResponseEntity.ok(vueloService.obtenerPorId(id));
    }

    @Operation(summary = "Registrar nuevo vuelo", description = "Crea un nuevo registro de vuelo y lo asocia a un avión existente mediante su matrícula.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vuelo registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = RegistroVueloDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (fechas erróneas, matrícula vacía...)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "El avión indicado por la matrícula no existe",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<RegistroVueloDTO> guardar(@Valid @RequestBody RegistroVuelo vuelo) {
        return new ResponseEntity<>(vueloService.guardar(vuelo), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar vuelo", description = "Modifica los datos de un vuelo existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vuelo actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Vuelo o Avión (si se intenta cambiar) no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RegistroVueloDTO> actualizar(@Parameter(description = "ID del vuelo a actualizar") @PathVariable Long id,
                                                       @RequestBody RegistroVuelo vuelo) {
        return ResponseEntity.ok(vueloService.actualizar(id, vuelo));
    }

    @Operation(summary = "Eliminar vuelo", description = "Borra un registro de vuelo del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vuelo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "El vuelo no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del vuelo a eliminar") @PathVariable Long id) {
        vueloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoints Extra ---

    @Operation(summary = "Consultar vuelos largos (JPQL)", description = "Obtiene vuelos cuya distancia supera los 5000km.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/largos")
    public ResponseEntity<List<RegistroVueloDTO>> getVuelosLargos() {
        return ResponseEntity.ok(vueloService.obtenerVuelosLargos());
    }

    @Operation(summary = "Consultar vuelos con incidencias (SQL Nativo)", description = "Obtiene vuelos que tienen el flag de incidencias activo.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/incidencias")
    public ResponseEntity<List<RegistroVueloDTO>> getConIncidencias() {
        return ResponseEntity.ok(vueloService.obtenerVuelosConIncidencias());
    }

    // --- Exception Handlers ---

    @ExceptionHandler(VueloNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Ayuda a Swagger a detectar el código
    public ResponseEntity<ErrorResponse> handleNotFound(VueloNotFoundException ex) {
        return new ResponseEntity<>(ErrorResponse.generalError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(ErrorResponse.validationError(errors), HttpStatus.BAD_REQUEST);
    }
}