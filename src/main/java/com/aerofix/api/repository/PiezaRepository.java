package com.aerofix.api.repository;

import com.aerofix.api.model.Pieza;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface PiezaRepository extends JpaRepository<Pieza, Long>, JpaSpecificationExecutor<Pieza> {

    // DERIVED QUERY
    boolean existsByReferencia(String referencia);

    // JPQL: Piezas críticas que necesitan revisión (revisadas antes de 2024)
    @Query("SELECT p FROM Pieza p WHERE p.esCritica = true AND p.fechaUltimaRevision < :fechaLimite")
    List<Pieza> findCriticasAntiguas(@Param("fechaLimite") LocalDate fechaLimite);

    // QL NATIVO: Piezas con stock bajo (menor que 10)
    @Query(value = "SELECT * FROM pieza WHERE stock < 10", nativeQuery = true)
    List<Pieza> findStockBajoNativo();

    // MÉTODO DEFAULT CON SPECIFICATIONS
    default List<Pieza> buscarConFiltros(String nombre, Boolean esCritica, Float precioMax) {
        Specification<Pieza> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nombre != null && !nombre.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nombre")),
                        "%" + nombre.toLowerCase() + "%"
                ));
            }
            if (esCritica != null) {
                predicates.add(criteriaBuilder.equal(root.get("esCritica"), esCritica));
            }
            if (precioMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("precioUnitario"), precioMax));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(spec);
    }
}