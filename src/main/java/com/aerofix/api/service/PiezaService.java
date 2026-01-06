package com.aerofix.api.service;

import com.aerofix.api.dto.PiezaDTO;
import com.aerofix.api.exception.PiezaNotFoundException;
import com.aerofix.api.model.Pieza;
import com.aerofix.api.repository.PiezaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PiezaService {

    @Autowired
    private PiezaRepository piezaRepository;

    @Autowired
    private ModelMapper modelMapper; // Magia aquí

    // Buscar con filtros
    public List<PiezaDTO> buscarPiezas(String nombre, Boolean esCritica, Float precioMax) {
        List<Pieza> lista = piezaRepository.buscarConFiltros(nombre, esCritica, precioMax);
        return mapList(lista);
    }

    public PiezaDTO obtenerPorId(Long id) {
        Pieza pieza = piezaRepository.findById(id)
                .orElseThrow(() -> new PiezaNotFoundException("Pieza con ID " + id + " no encontrada"));
        return toDTO(pieza);
    }

    public PiezaDTO guardarPieza(Pieza pieza) {
        return toDTO(piezaRepository.save(pieza));
    }

    // PATCH (Modificar)
    public PiezaDTO modificarPieza(Long id, Pieza nuevosDatos) {
        Pieza existente = piezaRepository.findById(id)
                .orElseThrow(PiezaNotFoundException::new);

        // Copia ignorando nulos (gracias a config de AppConfig)
        modelMapper.map(nuevosDatos, existente);
        existente.setId(id); // Proteger ID

        return toDTO(piezaRepository.save(existente));
    }

    public void eliminarPieza(Long id) {
        if (!piezaRepository.existsById(id)) {
            throw new PiezaNotFoundException();
        }
        piezaRepository.deleteById(id);
    }

    // Métodos Extra
    public List<PiezaDTO> obtenerStockBajo() {
        return mapList(piezaRepository.findStockBajoNativo());
    }

    // Helpers
    private PiezaDTO toDTO(Pieza entity) {
        PiezaDTO dto = modelMapper.map(entity, PiezaDTO.class);
        if (entity.getMantenimientosDondeSeUso() != null) {
            dto.setPiezasEmpleadas(entity.getMantenimientosDondeSeUso().size());
        }
        return dto;
    }

    private List<PiezaDTO> mapList(List<Pieza> list) {
        return list.stream().map(this::toDTO).toList();
    }
}