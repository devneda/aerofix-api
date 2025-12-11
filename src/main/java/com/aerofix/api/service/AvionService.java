package com.aerofix.api.service;

import com.aerofix.api.model.Avion;
import com.aerofix.api.repository.AvionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvionService {

    private final AvionRepository avionRepository;

    // Inyección de dependencias por constructor
    public AvionService(AvionRepository avionRepository) {
        this.avionRepository = avionRepository;
    }

    // 1. Crear o Actualizar un avión
    public Avion guardarAvion(Avion avion) {
        return avionRepository.save(avion);
    }

    // 2. Leer todos los aviones
    public List<Avion> obtenerTodos() {
        return avionRepository.findAll();
    }

    // 3. Leer un avión por ID (Devuelve un Optional para evitar NullPointerException)
    public Optional<Avion> obtenerPorId(Long id) {
        return avionRepository.findById(id);
    }

    // 4. Eliminar un avión
    public void eliminarAvion(Long id) {
        avionRepository.deleteById(id);
    }
}