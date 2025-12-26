package com.aerofix.api.service;

import com.aerofix.api.dto.MantenimientoDTO;
import com.aerofix.api.dto.MantenimientoMapper;
import com.aerofix.api.model.Mantenimiento;
import com.aerofix.api.repository.MantenimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MantenimientoService {

    private final MantenimientoRepository mantenimientoRepository;
    private final MantenimientoMapper mapper; // Necesitamos el mapper aquí

    public MantenimientoService(MantenimientoRepository mantenimientoRepository, MantenimientoMapper mapper) {
        this.mantenimientoRepository = mantenimientoRepository;
        this.mapper = mapper;
    }

    // Método refactorizado para soportar filtros
    public List<MantenimientoDTO> buscarMantenimientos(Boolean finalizado, Float costeMax, String descripcion) {
        // Llamamos al método default del repositorio
        List<Mantenimiento> resultados = mantenimientoRepository.buscarConFiltros(finalizado, costeMax, descripcion);

        return resultados.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MantenimientoDTO> obtenerPorId(String codigoOrden) {
        return mantenimientoRepository.findById(codigoOrden)
                .map(mapper::toDTO);
    }

    public MantenimientoDTO guardarMantenimiento(Mantenimiento mantenimiento) {
        Mantenimiento guardado = mantenimientoRepository.save(mantenimiento);
        return mapper.toDTO(guardado);
    }

    public void eliminarMantenimiento(String codigoOrigen) {
        mantenimientoRepository.deleteById(codigoOrigen);
    }
}