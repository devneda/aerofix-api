package com.aerofix.api.service;

import com.aerofix.api.model.Mantenimiento;
import com.aerofix.api.repository.MantenimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MantenimientoService {

    private final MantenimientoRepository mantenimientoRepository;

    public MantenimientoService(MantenimientoRepository mantenimientoRepository) {
        this.mantenimientoRepository = mantenimientoRepository;
    }

    public List<Mantenimiento> obtenerTodos() {
        return mantenimientoRepository.findAll();
    }

    public Optional<Mantenimiento> obtenerPorId(String codigoOrigen) {
        return mantenimientoRepository.findById(codigoOrigen);
    }

    public Mantenimiento guardarMantenimiento(Mantenimiento mantenimiento) {
        // Aquí Spring JPA es listo: si el objeto 'mantenimiento' trae
        // un objeto 'avion' con la matrícula rellena, él solo hace la unión.
        return mantenimientoRepository.save(mantenimiento);
    }

    public void eliminarMantenimiento(String codigoOrigen) {
        mantenimientoRepository.deleteById(codigoOrigen);
    }
}