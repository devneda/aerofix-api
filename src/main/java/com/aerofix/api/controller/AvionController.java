package com.aerofix.api.controller;

import com.aerofix.api.dto.AvionDTO;
import com.aerofix.api.model.Avion;
import com.aerofix.api.service.AvionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aviones")
public class AvionController {

    private final AvionService avionService;

    public AvionController(AvionService avionService) {
        this.avionService = avionService;
    }

    // FUSIONADO: Este método maneja tanto "Traer todos" como "Filtrar"
    // Si los params son null, el servicio devolverá la lista completa.
    @GetMapping
    public ResponseEntity<List<AvionDTO>> obtenerAviones(
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Boolean enServicio,
            @RequestParam(required = false) Float horasMax
    ) {
        List<AvionDTO> lista = avionService.buscarAviones(modelo, enServicio, horasMax);
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<AvionDTO> guardarAvion(@RequestBody Avion avion) {
        AvionDTO nuevoAvion = avionService.guardarAvion(avion);
        return ResponseEntity.status(201).body(nuevoAvion);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<AvionDTO> obtenerPorId(@PathVariable String matricula) {
        return avionService.obtenerPorId(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> eliminarAvion(@PathVariable String matricula) {
        if (avionService.obtenerPorId(matricula).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        avionService.eliminarAvion(matricula);
        return ResponseEntity.noContent().build();
    }
}