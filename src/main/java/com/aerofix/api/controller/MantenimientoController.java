package com.aerofix.api.controller;

import com.aerofix.api.dto.MantenimientoDTO;
import com.aerofix.api.exception.ErrorResponse;
import com.aerofix.api.exception.MantenimientoNotFoundException;
import com.aerofix.api.model.Mantenimiento;
import com.aerofix.api.service.MantenimientoService;
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
@RequestMapping("/api/mantenimientos")
public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    @GetMapping
    public ResponseEntity<List<MantenimientoDTO>> obtenerMantenimientos(
            @RequestParam(required = false) Boolean finalizado,
            @RequestParam(required = false) Float costeMax,
            @RequestParam(required = false) String descripcion
    ) {
        return ResponseEntity.ok(mantenimientoService.buscarMantenimientos(finalizado, costeMax, descripcion));
    }

    @PostMapping
    public ResponseEntity<MantenimientoDTO> guardar(@Valid @RequestBody Mantenimiento mantenimiento) {
        return new ResponseEntity<>(mantenimientoService.guardarMantenimiento(mantenimiento), HttpStatus.CREATED);
    }

    @GetMapping("/{codigoOrden}")
    public ResponseEntity<MantenimientoDTO> obtenerPorId(@PathVariable String codigoOrden) {
        return ResponseEntity.ok(mantenimientoService.obtenerPorId(codigoOrden));
    }

    @PutMapping("/{codigoOrden}")
    public ResponseEntity<MantenimientoDTO> modificar(@PathVariable String codigoOrden, @RequestBody Mantenimiento mantenimiento) {
        return ResponseEntity.ok(mantenimientoService.modificarMantenimiento(codigoOrden, mantenimiento));
    }

    @DeleteMapping("/{codigoOrden}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigoOrden) {
        mantenimientoService.eliminarMantenimiento(codigoOrden);
        return ResponseEntity.noContent().build();
    }

    // Endpoints Extra (Estadísticas)
    @GetMapping("/top-caros")
    public ResponseEntity<List<MantenimientoDTO>> getTopCaros() {
        return ResponseEntity.ok(mantenimientoService.obtenerTopCaros());
    }

    // --- EXCEPTION HANDLERS ---

    @ExceptionHandler(MantenimientoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(MantenimientoNotFoundException ex) {
        return new ResponseEntity<>(ErrorResponse.generalError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
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