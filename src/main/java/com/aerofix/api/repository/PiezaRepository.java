package com.aerofix.api.repository;

import com.aerofix.api.model.Pieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiezaRepository extends JpaRepository<Pieza, Long> {
}
