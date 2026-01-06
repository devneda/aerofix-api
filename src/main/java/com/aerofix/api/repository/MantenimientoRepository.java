package com.aerofix.api.repository;

import com.aerofix.api.model.Mantenimiento;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, String>, JpaSpecificationExecutor<Mantenimiento> {

    // QUERY METHOD
    List<Mantenimiento> findByFinalizado(boolean finalizado);

    // JPQL: Sumar coste total de mantenimientos finalizados (Estadística)
    @Query("SELECT SUM(m.costeTotal) FROM Mantenimiento m WHERE m.finalizado = true")
    Double sumarCosteMantenimientosFinalizados();

    // SQL NATIVO: Top 3 mantenimientos más caros (Reporte)
    // Nota: Asegúrate de usar el nombre real de tu tabla (ej: mantenimiento o mantenimiento_final)
    @Query(value = "SELECT * FROM mantenimiento ORDER BY coste_total DESC LIMIT 3", nativeQuery = true)
    List<Mantenimiento> findTop3CarosNativo();

    // Se usa Specifications para filtrado dinámico.
    default List<Mantenimiento> buscarConFiltros(Boolean finalizado, Float costeMax, String descripcion) {
        Specification<Mantenimiento> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (finalizado != null) {
                predicates.add(criteriaBuilder.equal(root.get("finalizado"), finalizado));
            }
            if (costeMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("costeTotal"), costeMax));
            }
            if (descripcion != null && !descripcion.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("descripcion")),
                        "%" + descripcion.toLowerCase() + "%"
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(spec);
    }
}