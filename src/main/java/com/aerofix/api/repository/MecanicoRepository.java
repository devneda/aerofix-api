package com.aerofix.api.repository;

import com.aerofix.api.model.Mecanico;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico, Long>, JpaSpecificationExecutor<Mecanico> {

    // Método default seguro contra errores de tipos en BDD
    default List<Mecanico> buscarConFiltros(Boolean disponible, Integer experienciaMin, String nombre) {

        Specification<Mecanico> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Filtro: Disponible (Exacto)
            if (disponible != null) {
                predicates.add(criteriaBuilder.equal(root.get("disponible"), disponible));
            }

            // 2. Filtro: Experiencia Mínima (Mayor o igual)
            if (experienciaMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("nivelExperiencia"), experienciaMin));
            }

            // 3. Filtro: Nombre (Contiene texto, ignora mayúsculas)
            if (nombre != null && !nombre.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nombre")),
                        "%" + nombre.toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return findAll(spec);
    }
}