package com.aerofix.api.repository;

import com.aerofix.api.model.Mantenimiento;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, String>, JpaSpecificationExecutor<Mantenimiento> {

    // Método default con la lógica de filtrado encapsulada
    default List<Mantenimiento> buscarConFiltros(Boolean finalizado, Float costeMax, String descripcion) {

        Specification<Mantenimiento> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Filtro: Finalizado (Exacto)
            if (finalizado != null) {
                predicates.add(criteriaBuilder.equal(root.get("finalizado"), finalizado));
            }

            // 2. Filtro: Coste Máximo (Menor o igual)
            if (costeMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("costeTotal"), costeMax));
            }

            // 3. Filtro: Descripción (Contiene texto, ignora mayúsculas)
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