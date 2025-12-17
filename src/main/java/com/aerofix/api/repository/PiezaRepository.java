package com.aerofix.api.repository;

import com.aerofix.api.model.Pieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PiezaRepository extends JpaRepository<Pieza, Long> {
    Optional<Pieza> findByReferencia(String referencia);
    void deleteByReferencia(String referencia);
}
