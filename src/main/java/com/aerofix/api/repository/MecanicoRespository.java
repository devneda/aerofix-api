package com.aerofix.api.repository;

import com.aerofix.api.model.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MecanicoRespository extends JpaRepository<Mecanico, Long> {

}
