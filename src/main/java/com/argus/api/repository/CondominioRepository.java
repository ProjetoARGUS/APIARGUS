package com.argus.api.repository;

import com.argus.api.domain.model.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CondominioRepository extends JpaRepository<Condominio, Long> {
    Optional<Condominio> findByNome(String nome);
    Optional<Condominio> findById(Long id);
}
