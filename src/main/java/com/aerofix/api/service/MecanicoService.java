package com.aerofix.api.service;

import com.aerofix.api.model.Mecanico;
import com.aerofix.api.repository.MecanicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MecanicoService {

    private final MecanicoRepository mecanicoRepository;

    public MecanicoService(MecanicoRepository mecanicoRepository) {
        this.mecanicoRepository = mecanicoRepository;
    }

    public List<Mecanico> obtenerTodos() {
        return mecanicoRepository.findAll();
    }

    public Optional<Mecanico> obtenerPorId(Long id) {
        return mecanicoRepository.findById(id);
    }

    public Mecanico actualizarMecanico(Long id, Mecanico nuevosDatos) {
        return mecanicoRepository.findById(id)
                .map(mecanicoExistente -> {
                    // Actualizamos campo a campo con los nuevos datos
                    mecanicoExistente.setNombre(nuevosDatos.getNombre());
                    mecanicoExistente.setLicenciaId(nuevosDatos.getLicenciaId());
                    mecanicoExistente.setNivelExperiencia(nuevosDatos.getNivelExperiencia());
                    mecanicoExistente.setSalarioHora(nuevosDatos.getSalarioHora());
                    mecanicoExistente.setDisponible(nuevosDatos.isDisponible());
                    mecanicoExistente.setFechaContratacion(nuevosDatos.getFechaContratacion());

                    // Guardamos los cambios (JPA detecta que ya tiene ID y hace un UPDATE en vez de INSERT)
                    return mecanicoRepository.save(mecanicoExistente);
                })
                .orElse(null); // Si no existe, devolvemos null (el controlador manejará el error 404)
    }

    public Mecanico guardarMecanico(Mecanico mecanico) {
        return mecanicoRepository.save(mecanico);
    }

    public void eliminarMecanico(Long id) {
        mecanicoRepository.deleteById(id);
    }
}