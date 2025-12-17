package com.aerofix.api.controller;

import com.aerofix.api.model.Mantenimiento;
import com.aerofix.api.service.MantenimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mantenimientos")
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;

    public MantenimientoController(MantenimientoService mantenimientoService) {
        this.mantenimientoService = mantenimientoService;
    }

    @GetMapping
    public List<Mantenimiento> obtenerTodos() {
        return mantenimientoService.obtenerTodos();
    }

    @GetMapping("/{codigoOrden}") // Cambiar variable
    public ResponseEntity<Mantenimiento> obtenerPorId(@PathVariable String codigoOrden) {
        return mantenimientoService.obtenerPorId(codigoOrden)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mantenimiento guardar(@RequestBody Mantenimiento mantenimiento) {
        return mantenimientoService.guardarMantenimiento(mantenimiento);
    }

    @DeleteMapping("/{codigoOrden}") // Cambiar variable
    public ResponseEntity<Void> eliminar(@PathVariable String codigoOrden) {
        if (mantenimientoService.obtenerPorId(codigoOrden).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mantenimientoService.eliminarMantenimiento(codigoOrden);
        return ResponseEntity.noContent().build();
    }
}