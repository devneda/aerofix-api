package com.aerofix.api.service;

import com.aerofix.api.model.RegistroVuelo;
import com.aerofix.api.repository.RegistroVueloRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroVueloService {

    private final RegistroVueloRepository registroVueloRepository;

    public RegistroVueloService(RegistroVueloRepository registroVueloRepository) {
        this.registroVueloRepository = registroVueloRepository;
    }

    public List<RegistroVuelo> obtenerTodos() {
        return registroVueloRepository.findAll();
    }

    public Optional<RegistroVuelo> obtenerPorId(Long id) {
        return registroVueloRepository.findById(id);
    }

    public RegistroVuelo guardar(RegistroVuelo vuelo) {
        return registroVueloRepository.save(vuelo);
    }

    public void eliminar(Long id) {
        registroVueloRepository.deleteById(id);
    }

    // Método PUT
    public RegistroVuelo actualizar(Long id, RegistroVuelo nuevosDatos) {
        return registroVueloRepository.findById(id)
                .map(vueloExistente -> {
                    vueloExistente.setCodigoVuelo(nuevosDatos.getCodigoVuelo());
                    vueloExistente.setOrigenDestino(nuevosDatos.getOrigenDestino());
                    vueloExistente.setDistanciaKm(nuevosDatos.getDistanciaKm());
                    vueloExistente.setCombustibleConsumido(nuevosDatos.getCombustibleConsumido());
                    vueloExistente.setIncidenciasReportadas(nuevosDatos.isIncidenciasReportadas());
                    vueloExistente.setFechaVuelo(nuevosDatos.getFechaVuelo());

                    // Permitir cambiar el avión de un vuelo ya registrado:
                    if (nuevosDatos.getAvion() != null) {
                        vueloExistente.setAvion(nuevosDatos.getAvion());
                    }

                    return registroVueloRepository.save(vueloExistente);
                })
                .orElse(null);
    }
}