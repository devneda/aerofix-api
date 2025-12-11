package com.aerofix.api.service;

import com.aerofix.api.model.Pieza;
import com.aerofix.api.repository.PiezaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PiezaService {

    private final PiezaRepository piezaRepository;

    // Inyección de dependencias por constructor
    public PiezaService(PiezaRepository piezaRepository) {
        this.piezaRepository = piezaRepository;
    }

    // 1. Crear o Actualizar un avión
    public Pieza guardarPieza(Pieza pieza) {
        return piezaRepository.save(pieza);
    }

    // 2. Leer todos los aviones
    public List<Pieza> obtenerTodos() {
        return piezaRepository.findAll();
    }

    // 3. Leer un avión por ID (Devuelve un Optional para evitar NullPointerException)
    public Optional<Pieza> obtenerPorId(Long id) {
        return piezaRepository.findById(id);
    }

    // 4. Eliminar un avión
    public void eliminarPieza(Long id) {
        piezaRepository.deleteById(id);
    }
}