package com.aerofix.api.controller;

import com.aerofix.api.dto.MantenimientoDTO;
import com.aerofix.api.dto.MantenimientoMapper;
import com.aerofix.api.model.Mantenimiento;
import com.aerofix.api.service.MantenimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mantenimientos")
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;
    private final MantenimientoMapper mapper; // <--- Inyectamos el Mapper

    public MantenimientoController(MantenimientoService mantenimientoService, MantenimientoMapper mapper) {
        this.mantenimientoService = mantenimientoService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MantenimientoDTO> obtenerTodos() {
        // Convertimos la lista de Entidades a lista de DTOs
        return mantenimientoService.obtenerTodos().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public MantenimientoDTO guardar(@RequestBody Mantenimiento mantenimiento) {
        // Guardamos la entidad (como antes)
        Mantenimiento guardado = mantenimientoService.guardarMantenimiento(mantenimiento);
        // Pero devolvemos el DTO limpio
        return mapper.toDTO(guardado);
    }

    @GetMapping("/{codigoOrden}")
    public ResponseEntity<MantenimientoDTO> obtenerPorId(@PathVariable String codigoOrden) {
        return mantenimientoService.obtenerPorId(codigoOrden)
                .map(mapper::toDTO) // Convertimos a DTO si existe
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // El Delete no cambia porque no devuelve body
    @DeleteMapping("/{codigoOrden}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigoOrden) {
        if (mantenimientoService.obtenerPorId(codigoOrden).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mantenimientoService.eliminarMantenimiento(codigoOrden);
        return ResponseEntity.noContent().build();
    }
}