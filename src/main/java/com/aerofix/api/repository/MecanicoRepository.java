package com.aerofix.api.repository;

import com.aerofix.api.model.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico, Long> {

    @Query("SELECT m FROM Mecanico m WHERE " +
            "(:disponible IS NULL OR m.disponible = :disponible) AND " +
            "(:experienciaMin IS NULL OR m.nivelExperiencia >= :experienciaMin) AND " +
            "(:nombre IS NULL OR LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Mecanico> buscarConFiltros(
            @Param("disponible") Boolean disponible,
            @Param("experienciaMin") Integer experienciaMin,
            @Param("nombre") String nombre);
}