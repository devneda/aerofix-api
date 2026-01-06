package com.aerofix.api.controller;

import com.aerofix.api.dto.PiezaDTO;
import com.aerofix.api.exception.ErrorResponse;
import com.aerofix.api.exception.PiezaNotFoundException;
import com.aerofix.api.model.Pieza;
import com.aerofix.api.service.PiezaService;
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
@RequestMapping("/api/piezas")
public class PiezaController {

    @Autowired
    private PiezaService piezaService;

    @GetMapping
    public ResponseEntity<List<PiezaDTO>> obtenerPiezas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Boolean esCritica,
            @RequestParam(required = false) Float precioMax
    ) {
        return ResponseEntity.ok(piezaService.buscarPiezas(nombre, esCritica, precioMax));
    }

    @PostMapping
    public ResponseEntity<PiezaDTO> guardar(@Valid @RequestBody Pieza pieza) {
        return new ResponseEntity<>(piezaService.guardarPieza(pieza), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PiezaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(piezaService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PiezaDTO> actualizar(@PathVariable Long id, @RequestBody Pieza pieza) {
        return ResponseEntity.ok(piezaService.modificarPieza(id, pieza));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        piezaService.eliminarPieza(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint Extra
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<PiezaDTO>> getStockBajo() {
        return ResponseEntity.ok(piezaService.obtenerStockBajo());
    }

    // EXCEPTION HANDLERS
    @ExceptionHandler(PiezaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(PiezaNotFoundException ex) {
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