package com.aerofix.api.controller;

import com.aerofix.api.dto.PiezaDTO;
import com.aerofix.api.dto.PiezaMapper;
import com.aerofix.api.model.Pieza;
import com.aerofix.api.service.PiezaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/piezas")
public class PiezaController {

    private final PiezaService piezaService;
    private final PiezaMapper mapper;

    public PiezaController(PiezaService piezaService, PiezaMapper mapper) {
        this.piezaService = piezaService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PiezaDTO> obtenerTodas() {
        return piezaService.obtenerTodas().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{referencia}")
    public ResponseEntity<PiezaDTO> obtenerPorReferencia(@PathVariable String referencia) {
        return piezaService.obtenerPorReferencia(referencia)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PiezaDTO guardar(@RequestBody Pieza pieza) {
        return mapper.toDTO(piezaService.guardarPieza(pieza));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PiezaDTO> actualizar(@PathVariable Long id, @RequestBody Pieza pieza) {
        Pieza piezaActualizada = piezaService.actualizarPieza(id, pieza);
        if (piezaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toDTO(piezaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (piezaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        piezaService.eliminarPieza(id);
        return ResponseEntity.noContent().build();
    }
}