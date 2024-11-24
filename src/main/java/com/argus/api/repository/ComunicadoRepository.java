package com.argus.api.repository;

import com.argus.api.domain.model.Comunicados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComunicadoRepository extends JpaRepository<Comunicados, Long> {
    Optional<Comunicados> findById(Long id);
}

