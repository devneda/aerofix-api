package com.aerofix.api.controller;

import com.aerofix.api.dto.MantenimientoDTO;
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

    // GET único que maneja "traer todos" o "filtrar"
    @GetMapping
    public ResponseEntity<List<MantenimientoDTO>> obtenerMantenimientos(
            @RequestParam(required = false) Boolean finalizado,
            @RequestParam(required = false) Float costeMax,
            @RequestParam(required = false) String descripcion
    ) {
        List<MantenimientoDTO> lista = mantenimientoService.buscarMantenimientos(finalizado, costeMax, descripcion);
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<MantenimientoDTO> guardar(@RequestBody Mantenimiento mantenimiento) {
        MantenimientoDTO guardado = mantenimientoService.guardarMantenimiento(mantenimiento);
        return ResponseEntity.status(201).body(guardado);
    }

    @GetMapping("/{codigoOrden}")
    public ResponseEntity<MantenimientoDTO> obtenerPorId(@PathVariable String codigoOrden) {
        return mantenimientoService.obtenerPorId(codigoOrden)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigoOrden}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigoOrden) {
        if (mantenimientoService.obtenerPorId(codigoOrden).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mantenimientoService.eliminarMantenimiento(codigoOrden);
        return ResponseEntity.noContent().build();
    }
}