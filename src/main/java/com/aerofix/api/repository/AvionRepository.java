package com.aerofix.api.repository;

import com.aerofix.api.model.Avion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvionRepository extends JpaRepository<Avion, String> {
}