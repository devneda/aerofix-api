package com.aerofix.api.service;

import com.aerofix.api.model.RegistroVuelo;
import com.aerofix.api.repository.RegistroVueloRepository;

import java.util.List;
import java.util.Optional;

public class RegistroVueloService {

    private final RegistroVueloRepository registroVueloRepository;

    public RegistroVueloService(RegistroVueloRepository registroVueloRepository) {
        this.registroVueloRepository = registroVueloRepository;
    }

    public RegistroVuelo guardarRegistroVuelo(RegistroVuelo registroVuelo) {
        return registroVueloRepository.save(registroVuelo);
    }

    public List<RegistroVuelo> obtenerTodos() {
        return registroVueloRepository.findAll();
    }

    public Optional<RegistroVuelo> obtenerPorId(Long id) {
        return  registroVueloRepository.findById(id);
    }

    public void eliminarRegistroVuelo(Long id) {
        registroVueloRepository.deleteById(id);
    }
}
