package com.aerofix.api.service;

import com.aerofix.api.dto.RegistroVueloDTO;
import com.aerofix.api.exception.VueloNotFoundException;
import com.aerofix.api.model.Avion;
import com.aerofix.api.model.RegistroVuelo;
import com.aerofix.api.repository.AvionRepository;
import com.aerofix.api.repository.RegistroVueloRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistroVueloService {

    @Autowired
    private RegistroVueloRepository registroVueloRepository;

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Buscar con filtros
    public List<RegistroVueloDTO> buscarVuelos(String destino, Boolean incidencias, String codigo) {
        List<RegistroVuelo> lista = registroVueloRepository.buscarConFiltros(destino, incidencias, codigo);
        return mapList(lista);
    }

    public RegistroVueloDTO obtenerPorId(Long id) {
        RegistroVuelo vuelo = registroVueloRepository.findById(id)
                .orElseThrow(() -> new VueloNotFoundException("Vuelo ID " + id + " no encontrado"));
        return toDTO(vuelo);
    }

    @Transactional
    public RegistroVueloDTO guardar(RegistroVuelo vuelo) {
        // Primero compruebo si el avión existe
        if (vuelo.getAvion() == null || vuelo.getAvion().getMatricula() == null) {
            throw new IllegalArgumentException("Es necesario especificar la matrícula del avión.");
        }
        // Obtenemos su matricula
        String matricula = vuelo.getAvion().getMatricula();

        // Buscamos el avión real en la BBDD usando la matrícula
        Avion avionReal = avionRepository.findById(matricula)
                .orElseThrow(() -> new VueloNotFoundException("No existe ningún avión con la matrícula: " + matricula));

        // Reemplazamos el objeto "falso" que venía del JSON por el objeto "real" de la BBDD
        vuelo.setAvion(avionReal);

        // Ahora sí, guardamos. Hibernate ya está feliz porque el avión es una entidad gestionada.
        // Antes daba problemas porque se le enviaba un objeto Avión incompleto.
        return toDTO(registroVueloRepository.save(vuelo));
    }

    // PATCH (Actualizar)
    @Transactional
    public RegistroVueloDTO actualizar(Long id, RegistroVuelo nuevosDatos) {
        RegistroVuelo existente = registroVueloRepository.findById(id)
                .orElseThrow(VueloNotFoundException::new);

        modelMapper.map(nuevosDatos, existente);

        // Si en la actualización intentan cambiar el avión, también hay que buscarlo
        if (nuevosDatos.getAvion() != null && nuevosDatos.getAvion().getMatricula() != null) {
            String nuevaMatricula = nuevosDatos.getAvion().getMatricula();
            Avion avionNuevo = avionRepository.findById(nuevaMatricula)
                    .orElseThrow(() -> new VueloNotFoundException("Avión no encontrado: " + nuevaMatricula));
            existente.setAvion(avionNuevo);
        }

        // Forzamos que el ID no cambie por seguridad
        existente.setId(id);

        return toDTO(registroVueloRepository.save(existente));
    }

    public void eliminar(Long id) {
        if (!registroVueloRepository.existsById(id)) {
            throw new VueloNotFoundException();
        }
        registroVueloRepository.deleteById(id);
    }

    // Métodos Extra
    public List<RegistroVueloDTO> obtenerVuelosLargos() {
        return mapList(registroVueloRepository.findVuelosTransoceanicos());
    }

    public List<RegistroVueloDTO> obtenerVuelosConIncidencias() {
        return mapList(registroVueloRepository.findVuelosConIncidenciasNativo());
    }

    // Helpers
    private RegistroVueloDTO toDTO(RegistroVuelo entity) {
        RegistroVueloDTO dto = modelMapper.map(entity, RegistroVueloDTO.class);
        if (entity.getAvion() != null) {
            dto.setMatriculaAvion(entity.getAvion().getMatricula());
        }
        return dto;
    }

    private List<RegistroVueloDTO> mapList(List<RegistroVuelo> list) {
        return list.stream().map(this::toDTO).toList();
    }
}