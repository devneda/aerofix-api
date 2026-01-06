package com.aerofix.api.repository;

import com.aerofix.api.model.Avion;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface AvionRepository extends JpaRepository<Avion, String>, JpaSpecificationExecutor<Avion> {

    // QUERY METHOD
    List<Avion> findByModelo(String modelo);

    // JPQL
    @Query("SELECT a FROM Avion a WHERE a.enServicio = true AND a.horasVuelo > :horas")
    List<Avion> findAvionesMuyUsados(float horas);

    // SQL NATIVO
    @Query(value = "SELECT * FROM avion_final WHERE capacidad_pasajeros > 150", nativeQuery = true)
    List<Avion> findAvionesGrandesNativo();

    // Se usa Specifications para filtrado dinámico.
    default List<Avion> buscarConEspecificacion(String modelo, Boolean enServicio, Float horasMax) {
        Specification<Avion> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (modelo != null && !modelo.isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("modelo")), "%" + modelo.toLowerCase() + "%"));
            }
            if (enServicio != null) {
                predicates.add(criteriaBuilder.equal(root.get("enServicio"), enServicio));
            }
            if (horasMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("horasVuelo"), horasMax));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(spec);
    }
}