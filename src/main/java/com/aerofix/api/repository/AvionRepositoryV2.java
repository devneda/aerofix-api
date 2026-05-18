package com.aerofix.api.repository;

import com.aerofix.api.model.AvionV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvionRepositoryV2 extends JpaRepository<AvionV2, String> {
}
