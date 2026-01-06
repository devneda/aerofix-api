package com.aerofix.api.repository;

import com.aerofix.api.model.Mecanico;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico, Long>, JpaSpecificationExecutor<Mecanico> {

    // DERIVED QUERY
    List<Mecanico> findByLicenciaId(String licenciaId);

    // JPQL: Buscar mecánicos "expertos" y disponibles
    @Query("SELECT m FROM Mecanico m WHERE m.nivelExperiencia >= 4 AND m.disponible = true")
    List<Mecanico> findExpertosDisponibles();

    // SQL NATIVO: Mecánicos caros contratados recientemente
    @Query(value = "SELECT * FROM mecanico WHERE salario_hora > 30 AND fecha_contratacion > '2023-01-01'", nativeQuery = true)
    List<Mecanico> findCarosYNuevos();

    // Se usa Specifications para filtrado dinámico.
    default List<Mecanico> buscarConFiltros(Boolean disponible, Integer experienciaMin, String nombre) {
        Specification<Mecanico> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (disponible != null) {
                predicates.add(criteriaBuilder.equal(root.get("disponible"), disponible));
            }
            if (experienciaMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("nivelExperiencia"), experienciaMin));
            }
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