package com.argus.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.argus.api.domain.model.SessaoVotacao;
import com.argus.api.domain.model.Usuarios;
import com.argus.api.domain.model.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    // Corrigir o método para buscar os votos pela sessão de votação
    List<Voto> findBySessaoVotacao(SessaoVotacao sessaoVotacao);
    
    // Verificar se um usuário já votou na sessão de votação
    boolean existsByUsuariosAndSessaoVotacao(Usuarios usuarios, SessaoVotacao sessaoVotacao);
    
    // Buscar votos pela proposta (presumindo que a SessaoVotacao tenha um campo 'proposta')
    List<Voto> findBySessaoVotacaoProposta(String proposta);
}

