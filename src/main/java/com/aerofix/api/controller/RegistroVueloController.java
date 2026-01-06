package com.aerofix.api.controller;

import com.aerofix.api.dto.RegistroVueloDTO;
import com.aerofix.api.exception.ErrorResponse;
import com.aerofix.api.exception.VueloNotFoundException;
import com.aerofix.api.model.RegistroVuelo;
import com.aerofix.api.service.RegistroVueloService;
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
public class RegistroVueloController {

    @Autowired
    private RegistroVueloService vueloService;

    @GetMapping
    public ResponseEntity<List<RegistroVueloDTO>> obtenerTodos(
            @RequestParam(required = false) String destino,
            @RequestParam(required = false) Boolean incidencias,
            @RequestParam(required = false) String codigo
    ) {
        return ResponseEntity.ok(vueloService.buscarVuelos(destino, incidencias, codigo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroVueloDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vueloService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<RegistroVueloDTO> guardar(@Valid @RequestBody RegistroVuelo vuelo) {
        return new ResponseEntity<>(vueloService.guardar(vuelo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroVueloDTO> actualizar(@PathVariable Long id, @RequestBody RegistroVuelo vuelo) {
        return ResponseEntity.ok(vueloService.actualizar(id, vuelo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vueloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints Extra
    @GetMapping("/largos")
    public ResponseEntity<List<RegistroVueloDTO>> getVuelosLargos() {
        return ResponseEntity.ok(vueloService.obtenerVuelosLargos());
    }

    @GetMapping("/incidencias")
    public ResponseEntity<List<RegistroVueloDTO>> getConIncidencias() {
        return ResponseEntity.ok(vueloService.obtenerVuelosConIncidencias());
    }

    // Exception Handlers
    @ExceptionHandler(VueloNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(VueloNotFoundException ex) {
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