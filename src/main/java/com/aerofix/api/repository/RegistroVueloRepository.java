package com.aerofix.api.repository;

import com.aerofix.api.model.RegistroVuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroVueloRepository extends JpaRepository<RegistroVuelo, Long> {

}
