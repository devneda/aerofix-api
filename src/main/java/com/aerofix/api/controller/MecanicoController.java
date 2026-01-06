package com.aerofix.api.controller;

import com.aerofix.api.dto.MecanicoDTO;
import com.aerofix.api.exception.ErrorResponse;
import com.aerofix.api.exception.MecanicoNotFoundException;
import com.aerofix.api.model.Mecanico;
import com.aerofix.api.service.MecanicoService;
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
@RequestMapping("/api/mecanicos")
public class MecanicoController {

    @Autowired
    private MecanicoService mecanicoService;

    @GetMapping
    public ResponseEntity<List<MecanicoDTO>> obtenerMecanicos(
            @RequestParam(required = false) Boolean disponible,
            @RequestParam(required = false) Integer experienciaMin,
            @RequestParam(required = false) String nombre
    ) {
        return ResponseEntity.ok(mecanicoService.buscarMecanicos(disponible, experienciaMin, nombre));
    }

    @PostMapping
    public ResponseEntity<MecanicoDTO> guardarMecanico(@Valid @RequestBody Mecanico mecanico) {
        return new ResponseEntity<>(mecanicoService.guardarMecanico(mecanico), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MecanicoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mecanicoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MecanicoDTO> modificar(@PathVariable Long id, @RequestBody Mecanico mecanico) {
        return ResponseEntity.ok(mecanicoService.modificarMecanico(id, mecanico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMecanico(@PathVariable Long id) {
        mecanicoService.eliminarMecanico(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint Extra
    @GetMapping("/expertos")
    public ResponseEntity<List<MecanicoDTO>> getExpertos() {
        return ResponseEntity.ok(mecanicoService.buscarExpertos());
    }

    // EXCEPTION HANDLERS
    @ExceptionHandler(MecanicoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(MecanicoNotFoundException ex) {
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