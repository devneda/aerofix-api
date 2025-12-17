package com.aerofix.api.controller;

import com.aerofix.api.dto.MecanicoDTO;
import com.aerofix.api.dto.MecanicoMapper;
import com.aerofix.api.model.Mecanico;
import com.aerofix.api.service.MecanicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mecanicos")
public class MecanicoController {

    private final MecanicoService mecanicoService;
    private final MecanicoMapper mapper;

    public MecanicoController(MecanicoService mecanicoService, MecanicoMapper mapper) {
        this.mecanicoService = mecanicoService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MecanicoDTO> obtenerTodos() {
        return mecanicoService.obtenerTodos().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MecanicoDTO> obtenerPorId(@PathVariable Long id) {
        return mecanicoService.obtenerPorId(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MecanicoDTO> actualizar(@PathVariable Long id, @RequestBody Mecanico mecanicoDatos) {
        // Llamamos al servicio para actualizar
        Mecanico mecanicoActualizado = mecanicoService.actualizarMecanico(id, mecanicoDatos);

        if (mecanicoActualizado == null) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no existía el ID
        }

        // Convertimos la entidad actualizada a DTO y devolvemos 200 OK
        return ResponseEntity.ok(mapper.toDTO(mecanicoActualizado));
    }

    @PostMapping
    public MecanicoDTO guardar(@RequestBody Mecanico mecanico) {
        // Guardamos y devolvemos el DTO
        return mapper.toDTO(mecanicoService.guardarMecanico(mecanico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (mecanicoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mecanicoService.eliminarMecanico(id);
        return ResponseEntity.noContent().build();
    }
}