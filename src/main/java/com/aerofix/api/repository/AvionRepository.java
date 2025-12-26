package com.aerofix.api.repository;

import com.aerofix.api.model.Avion;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface AvionRepository extends JpaRepository<Avion, String>, JpaSpecificationExecutor<Avion> {

    // Método DEFAULT: Vive dentro del repositorio y contiene la lógica.
    // Ya no necesitas clases externas.
    default List<Avion> buscarConEspecificacion(String modelo, Boolean enServicio, Float horasMax) {

        // Definimos la especificación aquí mismo (expresión lambda)
        Specification<Avion> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Filtro Modelo (Safe LowerCase)
            if (modelo != null && !modelo.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("modelo")),
                        "%" + modelo.toLowerCase() + "%"
                ));
            }

            // 2. Filtro Estado
            if (enServicio != null) {
                predicates.add(criteriaBuilder.equal(root.get("enServicio"), enServicio));
            }

            // 3. Filtro Horas
            if (horasMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("horasVuelo"), horasMax));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Ejecutamos la búsqueda usando el método nativo de JpaSpecificationExecutor
        return findAll(spec);
    }
}