package com.argus.api.repository;

import com.argus.api.domain.model.Ocorrencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OcorrenciasRepository extends JpaRepository<Ocorrencias, Long> {
    List<Ocorrencias> findByUsuarioId(Long usuarioId);
    List<Ocorrencias> findByTipo(Ocorrencias.TipoOcorrencia tipo);
    List<Ocorrencias> findByStatusAprovacao(Ocorrencias.StatusAprovacao statusAprovacao);
    List<Ocorrencias> findByStatusResolucao(Ocorrencias.StatusResolucao statusResolucao);
}