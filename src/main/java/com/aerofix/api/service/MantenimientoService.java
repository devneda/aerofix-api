package com.aerofix.api.service;

import com.aerofix.api.dto.MantenimientoDTO;
import com.aerofix.api.exception.MantenimientoNotFoundException;
import com.aerofix.api.model.Mantenimiento;
import com.aerofix.api.repository.MantenimientoRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Buscar con filtros (Specifications)
    public List<MantenimientoDTO> buscarMantenimientos(Boolean finalizado, Float costeMax, String descripcion) {
        List<Mantenimiento> lista = mantenimientoRepository.buscarConFiltros(finalizado, costeMax, descripcion);
        return mapList(lista);
    }

    // Obtener por ID
    public MantenimientoDTO obtenerPorId(String codigoOrden) {
        Mantenimiento mantenimiento = mantenimientoRepository.findById(codigoOrden)
                .orElseThrow(() -> new MantenimientoNotFoundException("Orden " + codigoOrden + " no encontrada"));
        return toDTO(mantenimiento);
    }

    // Guardar
    public MantenimientoDTO guardarMantenimiento(Mantenimiento mantenimiento) {
        Mantenimiento guardado = mantenimientoRepository.save(mantenimiento);
        return toDTO(guardado);
    }

    // Modificar (Patching style)
    public MantenimientoDTO modificarMantenimiento(String codigoOrden, Mantenimiento nuevosDatos) {
        Mantenimiento existente = mantenimientoRepository.findById(codigoOrden)
                .orElseThrow(MantenimientoNotFoundException::new);

        // Copiamos datos nuevos sobre el viejo
        modelMapper.map(nuevosDatos, existente);
        existente.setCodigoOrden(codigoOrden); // Aseguramos ID

        return toDTO(mantenimientoRepository.save(existente));
    }

    public void eliminarMantenimiento(String codigoOrden) {
        if (!mantenimientoRepository.existsById(codigoOrden)) {
            throw new MantenimientoNotFoundException();
        }
        mantenimientoRepository.deleteById(codigoOrden);
    }

    // --- Métodos Extra para Puntos Extra ---

    public List<MantenimientoDTO> obtenerTopCaros() {
        return mapList(mantenimientoRepository.findTop3CarosNativo());
    }

    public Double obtenerGastoTotalFinalizados() {
        return mantenimientoRepository.sumarCosteMantenimientosFinalizados();
    }

    // --- Helpers Privados para Mapping ---

    private MantenimientoDTO toDTO(Mantenimiento entity) {
        MantenimientoDTO dto = modelMapper.map(entity, MantenimientoDTO.class);
        // Ajuste manual pequeño para asegurar que la matrícula del avión se pasa bien
        // (ya que ModelMapper a veces necesita configuración extra para anidados)
        if (entity.getAvion() != null) {
            dto.setMatriculaAvion(entity.getAvion().getMatricula());
        }
        return dto;
    }

    private List<MantenimientoDTO> mapList(List<Mantenimiento> list) {
        // Usamos stream para aplicar el toDTO corregido a cada elemento
        return list.stream().map(this::toDTO).toList();
    }
}