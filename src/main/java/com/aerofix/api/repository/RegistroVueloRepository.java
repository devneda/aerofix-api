package com.aerofix.api.repository;

import com.aerofix.api.model.RegistroVuelo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface RegistroVueloRepository extends JpaRepository<RegistroVuelo, Long>, JpaSpecificationExecutor<RegistroVuelo> {

    // DERIVED QUERY
    List<RegistroVuelo> findByCodigoVuelo(String codigoVuelo);

    // JPQL: Vuelos de larga distancia (más de 5000km)
    @Query("SELECT r FROM RegistroVuelo r WHERE r.distanciaKm > 5000")
    List<RegistroVuelo> findVuelosTransoceanicos();

    // SQL NATIVO: Vuelos con incidencias reportadas
    @Query(value = "SELECT * FROM registro_vuelo WHERE incidencias_reportadas = true", nativeQuery = true)
    List<RegistroVuelo> findVuelosConIncidenciasNativo();

    // SPECIFICATIONS (Filtros dinámicos)
    default List<RegistroVuelo> buscarConFiltros(String destino, Boolean incidencias, String codigo) {
        Specification<RegistroVuelo> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (destino != null && !destino.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("origenDestino")),
                        "%" + destino.toLowerCase() + "%"
                ));
            }
            if (incidencias != null) {
                predicates.add(criteriaBuilder.equal(root.get("incidenciasReportadas"), incidencias));
            }
            if (codigo != null && !codigo.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("codigoVuelo")),
                        "%" + codigo.toLowerCase() + "%"
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(spec);
    }
}