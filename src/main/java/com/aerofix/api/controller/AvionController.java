package com.aerofix.api.controller;

import com.aerofix.api.dto.AvionDTO;
import com.aerofix.api.exception.AvionNotFoundException;
import com.aerofix.api.exception.ErrorResponse;
import com.aerofix.api.model.Avion;
import com.aerofix.api.service.AvionService;
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
@RequestMapping("/api/aviones")
public class AvionController {

    @Autowired
    private AvionService avionService;

    @GetMapping
    public ResponseEntity<List<AvionDTO>> obtenerAviones(
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Boolean enServicio,
            @RequestParam(required = false) Float horasMax
    ) {
        return ResponseEntity.ok(avionService.buscarAviones(modelo, enServicio, horasMax));
    }

    @GetMapping("/grandes")
    public ResponseEntity<List<AvionDTO>> getAvionesGrandes() {
        return ResponseEntity.ok(avionService.obtenerAvionesGrandes());
    }

    @PostMapping
    public ResponseEntity<AvionDTO> guardarAvion(@Valid @RequestBody Avion avion) {
        return new ResponseEntity<>(avionService.guardarAvion(avion), HttpStatus.CREATED);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<AvionDTO> obtenerPorId(@PathVariable String matricula) {
        return ResponseEntity.ok(avionService.obtenerPorId(matricula));
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<AvionDTO> modificarAvion(@PathVariable String matricula, @RequestBody Avion avion) {
        return ResponseEntity.ok(avionService.modificarAvion(matricula, avion));
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> eliminarAvion(@PathVariable String matricula) {
        avionService.eliminarAvion(matricula);
        return ResponseEntity.noContent().build();
    }

    // --- EXCEPTION HANDLERS (Estilo Profesor) ---

    @ExceptionHandler(AvionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(AvionNotFoundException ex) {
        return new ResponseEntity<>(ErrorResponse.generalError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(ErrorResponse.validationError(errors), HttpStatus.BAD_REQUEST);
    }
}