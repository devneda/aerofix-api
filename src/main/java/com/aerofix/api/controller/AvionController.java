package com.aerofix.api.controller;

import com.aerofix.api.model.Avion;
import com.aerofix.api.service.AvionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esto responderá JSON
@RequestMapping("/api/aviones") // La URL base será http://localhost:8080/api/aviones
public class AvionController {

    private final AvionService avionService;

    public AvionController(AvionService avionService) {
        this.avionService = avionService;
    }

    // 1. GET: Obtener todos los aviones
    @GetMapping
    public List<Avion> obtenerTodos() {
        return avionService.obtenerTodos();
    }

    // 2. POST: Guardar un nuevo avión
    @PostMapping
    public Avion guardarAvion(@RequestBody Avion avion) {
        return avionService.guardarAvion(avion);
    }

    // 3. GET: Obtener un avión por Matrícula
    @GetMapping("/{matricula}")
    public ResponseEntity<Avion> obtenerPorId(@PathVariable String matricula) {
        // Buscamos el avión. map(ResponseEntity::ok) devuelve 200 si existe.
        // orElse(...) devuelve 404 Not Found si no existe.
        return avionService.obtenerPorId(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. DELETE: Eliminar un avión
    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> eliminarAvion(@PathVariable String matricula) {
        // Primero verificamos si existe (opcional, pero buena práctica)
        if (avionService.obtenerPorId(matricula).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        avionService.eliminarAvion(matricula);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content (éxito sin body)
    }
}