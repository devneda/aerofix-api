package com.aerofix.api.service;

import com.aerofix.api.dto.MecanicoDTO;
import com.aerofix.api.dto.MecanicoMapper;
import com.aerofix.api.model.Mecanico;
import com.aerofix.api.repository.MecanicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MecanicoService {

    private final MecanicoRepository mecanicoRepository;
    private final MecanicoMapper mecanicoMapper;

    public MecanicoService(MecanicoRepository mecanicoRepository, MecanicoMapper mecanicoMapper) {
        this.mecanicoRepository = mecanicoRepository;
        this.mecanicoMapper = mecanicoMapper;
    }

    public MecanicoDTO guardarMecanico(Mecanico mecanico) {
        return mecanicoMapper.toDTO(mecanicoRepository.save(mecanico));
    }

    public List<MecanicoDTO> buscarMecanicos(Boolean disponible, Integer experienciaMin, String nombre) {
        List<Mecanico> lista = mecanicoRepository.buscarConFiltros(disponible, experienciaMin, nombre);
        return lista.stream()
                .map(mecanicoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MecanicoDTO> obtenerPorId(Long id) {
        return mecanicoRepository.findById(id).map(mecanicoMapper::toDTO);
    }

    public void eliminarMecanico(Long id) {
        mecanicoRepository.deleteById(id);
    }
}