package com.aerofix.api.service;

import com.aerofix.api.dto.MecanicoDTO;
import com.aerofix.api.exception.MecanicoNotFoundException;
import com.aerofix.api.model.Mecanico;
import com.aerofix.api.repository.MecanicoRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MecanicoService {

    @Autowired
    private MecanicoRepository mecanicoRepository;

    @Autowired
    private ModelMapper modelMapper; // Usamos el Bean de AppConfig

    // Buscar (Specifications)
    public List<MecanicoDTO> buscarMecanicos(Boolean disponible, Integer experienciaMin, String nombre) {
        List<Mecanico> lista = mecanicoRepository.buscarConFiltros(disponible, experienciaMin, nombre);
        return mapList(lista);
    }

    // Obtener por ID
    public MecanicoDTO obtenerPorId(Long id) {
        Mecanico mecanico = mecanicoRepository.findById(id)
                .orElseThrow(() -> new MecanicoNotFoundException("Mecánico con ID " + id + " no encontrado"));
        return toDTO(mecanico);
    }

    // Guardar
    public MecanicoDTO guardarMecanico(Mecanico mecanico) {
        Mecanico guardado = mecanicoRepository.save(mecanico);
        return toDTO(guardado);
    }

    // Modificar (PATCH)
    public MecanicoDTO modificarMecanico(Long id, Mecanico nuevosDatos) {
        Mecanico existente = mecanicoRepository.findById(id)
                .orElseThrow(MecanicoNotFoundException::new);

        // Copiamos datos nuevos sobre el viejo (ignora nulos gracias a AppConfig)
        modelMapper.map(nuevosDatos, existente);
        existente.setId(id); // Aseguramos ID

        return toDTO(mecanicoRepository.save(existente));
    }

    public void eliminarMecanico(Long id) {
        if (!mecanicoRepository.existsById(id)) {
            throw new MecanicoNotFoundException();
        }
        mecanicoRepository.deleteById(id);
    }

    // Métodos Extra
    public List<MecanicoDTO> buscarExpertos() {
        return mapList(mecanicoRepository.findExpertosDisponibles());
    }

    // Helpers privados para mapeo limpio
    private MecanicoDTO toDTO(Mecanico entity) {
        MecanicoDTO dto = modelMapper.map(entity, MecanicoDTO.class);
        // Ajuste manual para el contador de mantenimientos (si hiciera falta)
        if (entity.getMantenimientosAsignados() != null) {
            dto.setTotalMantenimientosAsignados(entity.getMantenimientosAsignados().size());
        }
        return dto;
    }

    private List<MecanicoDTO> mapList(List<Mecanico> list) {
        return list.stream().map(this::toDTO).toList();
    }
}