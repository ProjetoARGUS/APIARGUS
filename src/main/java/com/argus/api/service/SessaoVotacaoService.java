package com.argus.api.service;

import com.argus.api.domain.model.Condominio;
import com.argus.api.domain.model.SessaoVotacao;
import com.argus.api.dto.SessaoVotacaoDTO;
import com.argus.api.repository.CondominioRepository;
import com.argus.api.repository.SessaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessaoVotacaoService {

    private final SessaoVotacaoRepository sessaoVotacaoRepository;
    private final CondominioRepository condominioRepository;

    @Autowired
    public SessaoVotacaoService(SessaoVotacaoRepository sessaoVotacaoRepository, CondominioRepository condominioRepository) {
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.condominioRepository = condominioRepository;
    }

    public SessaoVotacaoDTO criarSessao(SessaoVotacaoDTO sessaoVotacaoDTO) {
        // Buscar o condomínio pelo nome
        Optional<Condominio> condominioOptional = condominioRepository.findByNome(sessaoVotacaoDTO.condominioNome());

        // Se o condomínio não existir, você pode lançar uma exceção ou criar um novo condomínio
        Condominio condominio;
        if (condominioOptional.isPresent()) {
            condominio = condominioOptional.get();
        } else {
            condominio = new Condominio();
            condominio.setNome(sessaoVotacaoDTO.condominioNome());
            condominio = condominioRepository.save(condominio);  // Persistir o novo condomínio
        }

        // Criar a sessão de votação
        SessaoVotacao sessaoVotacao = new SessaoVotacao();
        sessaoVotacao.setProposta(sessaoVotacaoDTO.proposta());
        sessaoVotacao.setDescricao(sessaoVotacaoDTO.descricao());
        sessaoVotacao.setDataInicio(sessaoVotacaoDTO.dataInicio());
        sessaoVotacao.setDataFim(sessaoVotacaoDTO.dataFim());
        sessaoVotacao.setCondominio(condominio);

        // Salvar a sessão de votação
        SessaoVotacao sessaoSalva = sessaoVotacaoRepository.save(sessaoVotacao);

        // Retornar o DTO da SessaoVotacao salva utilizando o método auxiliar
        return convertToDTO(sessaoSalva);
    }

    public List<SessaoVotacaoDTO> listarTodasSessoes() {
        List<SessaoVotacao> sessoes = sessaoVotacaoRepository.findAll();
        return sessoes.stream()
                .map(this::convertToDTO) // Método auxiliar para conversão
                .collect(Collectors.toList());
    }

    public void deletarSessao(Long id) {
        SessaoVotacao sessao = sessaoVotacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão de votação não encontrada com ID: " + id));
        sessaoVotacaoRepository.delete(sessao);
    }

    // Método auxiliar para converter SessaoVotacao para SessaoVotacaoDTO
    private SessaoVotacaoDTO convertToDTO(SessaoVotacao sessaoVotacao) {
        return new SessaoVotacaoDTO(
                sessaoVotacao.getId(),
                sessaoVotacao.getProposta(),
                sessaoVotacao.getDescricao(),
                sessaoVotacao.getDataInicio(),
                sessaoVotacao.getDataFim(),
                sessaoVotacao.getCondominio().getNome()
        );
    }
}
