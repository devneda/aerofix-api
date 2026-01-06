package com.aerofix.api.service;

import com.aerofix.api.dto.AvionDTO;
import com.aerofix.api.exception.AvionNotFoundException;
import com.aerofix.api.model.Avion;
import com.aerofix.api.repository.AvionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvionService {

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private ModelMapper modelMapper; // ¡Magia!

    // Guardar
    public AvionDTO guardarAvion(Avion avion) {
        Avion guardado = avionRepository.save(avion);
        return modelMapper.map(guardado, AvionDTO.class);
    }

    public List<AvionDTO> obtenerAvionesGrandes() {
        // Llamamos a la consulta nativa del repositorio
        List<Avion> aviones = avionRepository.findAvionesGrandesNativo();
        // Convertimos la lista de Entidades a DTOs (asumiendo que tienes un método o mapper para esto)
        return aviones.stream()
                .map(avion -> modelMapper.map(avion, AvionDTO.class)) // O tu método toDTO(avion)
                .toList();
    }

    // Buscar con filtros (Especificaciones)
    public List<AvionDTO> buscarAviones(String modelo, Boolean enServicio, Float horasMax) {
        List<Avion> resultados = avionRepository.buscarConEspecificacion(modelo, enServicio, horasMax);
        // Truco de ModelMapper para listas
        return modelMapper.map(resultados, new TypeToken<List<AvionDTO>>() {}.getType());
    }

    // Obtener por ID (Lanzando Excepción Custom)
    public AvionDTO obtenerPorId(String matricula) {
        Avion avion = avionRepository.findById(matricula)
                .orElseThrow(() -> new AvionNotFoundException("El avión con matrícula " + matricula + " no existe"));

        return modelMapper.map(avion, AvionDTO.class);
    }

    // Modificar (Estilo Profesor: Patching)
    public AvionDTO modificarAvion(String matricula, Avion avionDatosNuevos) {
        Avion avionExistente = avionRepository.findById(matricula)
                .orElseThrow(() -> new AvionNotFoundException("Avión no encontrado"));

        // Esto copia las propiedades que vengan en 'avionDatosNuevos' sobre 'avionExistente'
        modelMapper.map(avionDatosNuevos, avionExistente);
        // Aseguramos que la ID no cambie
        avionExistente.setMatricula(matricula);

        Avion guardado = avionRepository.save(avionExistente);
        return modelMapper.map(guardado, AvionDTO.class);
    }

    public void eliminarAvion(String matricula) {
        Avion avion = avionRepository.findById(matricula)
                .orElseThrow(AvionNotFoundException::new);
        avionRepository.delete(avion);
    }

    // Métodos EXTRA para probar JPQL y Nativo
    public List<AvionDTO> buscarMuyUsados(float horas) {
        return modelMapper.map(avionRepository.findAvionesMuyUsados(horas), new TypeToken<List<AvionDTO>>() {}.getType());
    }
}