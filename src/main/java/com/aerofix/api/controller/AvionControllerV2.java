package com.aerofix.api.controller;

import com.aerofix.api.model.AvionV2;
import com.aerofix.api.service.AvionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/aviones")
public class AvionControllerV2 {
    @Autowired
    private AvionService avionService;

    @GetMapping
    public ResponseEntity<List<AvionV2>> getAll() {
        return ResponseEntity.ok(avionService.findAllV2());
    }

    @PostMapping
    public ResponseEntity<AvionV2> add(@Valid @RequestBody AvionV2 avionV2) {
        AvionV2 nuevoAvion = avionService.guardadAvionV2(avionV2);
        return new ResponseEntity<>(nuevoAvion, HttpStatus.CREATED);
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<AvionV2> modificar(@PathVariable String matricula, @Valid @RequestBody AvionV2 avionV2) {
        return ResponseEntity.ok(avionService.modificarAvionV2(matricula, avionV2));
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable String matricula) {
        avionService.eliminarAvionV2(matricula);
        return ResponseEntity.noContent().build();
    }
}
