package com.aerofix.api.controller;

import com.aerofix.api.dto.MecanicoDTO;
import com.aerofix.api.model.Mecanico;
import com.aerofix.api.service.MecanicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mecanicos")
public class MecanicoController {

    private final MecanicoService mecanicoService;

    public MecanicoController(MecanicoService mecanicoService) {
        this.mecanicoService = mecanicoService;
    }

    // GET con filtros (y lista completa si no hay params)
    @GetMapping
    public ResponseEntity<List<MecanicoDTO>> obtenerMecanicos(
            @RequestParam(required = false) Boolean disponible,
            @RequestParam(required = false) Integer experienciaMin,
            @RequestParam(required = false) String nombre
    ) {
        List<MecanicoDTO> lista = mecanicoService.buscarMecanicos(disponible, experienciaMin, nombre);
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<MecanicoDTO> guardarMecanico(@RequestBody Mecanico mecanico) {
        MecanicoDTO nuevo = mecanicoService.guardarMecanico(mecanico);
        return ResponseEntity.status(201).body(nuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MecanicoDTO> obtenerPorId(@PathVariable Long id) {
        return mecanicoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMecanico(@PathVariable Long id) {
        if (mecanicoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mecanicoService.eliminarMecanico(id);
        return ResponseEntity.noContent().build();
    }
}