package com.argus.api.repository;

import com.argus.api.domain.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {
    List<SessaoVotacao> findByCondominio_Nome(String Nome);
    List<SessaoVotacao> findAll();
}

