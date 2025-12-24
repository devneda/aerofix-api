package com.aerofix.api.service;

import com.aerofix.api.dto.AvionDTO;
import com.aerofix.api.dto.AvionMapper;
import com.aerofix.api.model.Avion;
import com.aerofix.api.repository.AvionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvionService {

    private final AvionRepository avionRepository;
    private final AvionMapper avionMapper; // Inyectamos el mapper

    public AvionService(AvionRepository avionRepository, AvionMapper avionMapper) {
        this.avionRepository = avionRepository;
        this.avionMapper = avionMapper;
    }

    // Guardar (Mantenemos input entidad para simplificar el POST por ahora,
    // pero devolvemos DTO para confirmar lo guardado)
    public AvionDTO guardarAvion(Avion avion) {
        Avion guardado = avionRepository.save(avion);
        return avionMapper.toDTO(guardado);
    }

    // Obtener todos -> Devuelve lista de DTOs
    public List<AvionDTO> obtenerTodos() {
        return avionRepository.findAll().stream()
                .map(avionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener por ID -> Devuelve Optional<AvionDTO>
    public Optional<AvionDTO> obtenerPorId(String matricula) {
        return avionRepository.findById(matricula)
                .map(avionMapper::toDTO);
    }

    // Método para búsqueda con filtros
    public List<AvionDTO> buscarAviones(String modelo, Boolean enServicio, Float horasMax) {
        List<Avion> resultados = avionRepository.buscarConFiltros(modelo, enServicio, horasMax);
        // Reutilizamos el mapper para convertir la lista de entidades a DTOs
        return resultados.stream()
                .map(avionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void eliminarAvion(String matricula) {
        avionRepository.deleteById(matricula);
    }
}