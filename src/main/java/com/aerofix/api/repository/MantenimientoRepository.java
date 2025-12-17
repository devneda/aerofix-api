package com.aerofix.api.repository;

import com.aerofix.api.model.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, String> {
}