package com.aerofix.api.controller;

import com.aerofix.api.dto.RegistroVueloDTO;
import com.aerofix.api.dto.RegistroVueloMapper;
import com.aerofix.api.model.RegistroVuelo;
import com.aerofix.api.service.RegistroVueloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vuelos") // Endpoint: /api/vuelos
public class RegistroVueloController {

    private final RegistroVueloService vueloService;
    private final RegistroVueloMapper mapper;

    public RegistroVueloController(RegistroVueloService vueloService, RegistroVueloMapper mapper) {
        this.vueloService = vueloService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<RegistroVueloDTO> obtenerTodos() {
        return vueloService.obtenerTodos().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroVueloDTO> obtenerPorId(@PathVariable Long id) {
        return vueloService.obtenerPorId(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public RegistroVueloDTO guardar(@RequestBody RegistroVuelo vuelo) {
        return mapper.toDTO(vueloService.guardar(vuelo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroVueloDTO> actualizar(@PathVariable Long id, @RequestBody RegistroVuelo vuelo) {
        RegistroVuelo actualizado = vueloService.actualizar(id, vuelo);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (vueloService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        vueloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}