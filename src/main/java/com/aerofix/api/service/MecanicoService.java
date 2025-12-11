package com.aerofix.api.service;

import com.aerofix.api.model.Mecanico;
import com.aerofix.api.repository.MecanicoRespository;

import java.util.List;
import java.util.Optional;

public class MecanicoService {

    private final MecanicoRespository mecanicoRespository;

    public MecanicoService(MecanicoRespository mecanicoRespository) {
        this.mecanicoRespository = mecanicoRespository;
    }

    public Mecanico guardarMecanico(Mecanico mecanico) {
        return mecanicoRespository.save(mecanico);
    }

    public List<Mecanico> obtenerTodos() {
        return mecanicoRespository.findAll();
    }

    public Optional<Mecanico> obtenerPorId(Long id) {
        return mecanicoRespository.findById(id);
    }

    public void eliminarMecanico(Long id){
        mecanicoRespository.deleteById(id);
    }
}
