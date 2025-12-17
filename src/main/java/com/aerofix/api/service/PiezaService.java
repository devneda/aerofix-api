package com.aerofix.api.service;

import com.aerofix.api.model.Pieza;
import com.aerofix.api.repository.PiezaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PiezaService {

    private final PiezaRepository piezaRepository;

    public PiezaService(PiezaRepository piezaRepository) {
        this.piezaRepository = piezaRepository;
    }

    public List<Pieza> obtenerTodas() {
        return piezaRepository.findAll();
    }

    // ID es String
    public Optional<Pieza> obtenerPorReferencia(String referencia) {
        return piezaRepository.findByReferencia(referencia);
    }

    // ID es String
    public Optional<Pieza> obtenerPorId(Long id) {
        return piezaRepository.findById(id);
    }

    public Pieza guardarPieza(Pieza pieza) {
        return piezaRepository.save(pieza);
    }

    // ID es String
    public void eliminarPieza(Long id) {
        piezaRepository.deleteById(id);
    }

    // Método PUT
    public Pieza actualizarPieza(Long id, Pieza nuevosDatos) {
        return piezaRepository.findById(id)
                .map(piezaExistente -> {
                    piezaExistente.setReferencia(nuevosDatos.getReferencia());
                    piezaExistente.setNombre(nuevosDatos.getNombre());
                    piezaExistente.setStock(nuevosDatos.getStock());
                    piezaExistente.setPrecioUnitario(nuevosDatos.getPrecioUnitario());
                    piezaExistente.setEsCritica(nuevosDatos.isEsCritica());
                    piezaExistente.setFechaUltimaRevision(nuevosDatos.getFechaUltimaRevision());
                    // La referencia (ID) normalmente no se cambia
                    return piezaRepository.save(piezaExistente);
                })
                .orElse(null);
    }
}