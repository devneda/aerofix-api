package com.aerofix.api.repository;

import com.aerofix.api.model.Avion;
import org.springframework.data.jpa.repository.JpaRepository; // <--- Importante
import org.springframework.stereotype.Repository;

@Repository
public interface AvionRepository extends JpaRepository<Avion, Long> {
    // Al extender, YA TIENES save(), findAll(), delete(), etc.
}