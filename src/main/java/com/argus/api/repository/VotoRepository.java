package com.argus.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ArgusAPI.domain.model.SessaoVotacao;
import com.ArgusAPI.domain.model.Usuarios;
import com.ArgusAPI.domain.model.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findById(Long id);
    List<Voto> findBySessaoVotacao(SessaoVotacao sessaoVotacao);
}