package com.argus.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ArgusAPI.domain.model.SessaoVotacao;
import com.ArgusAPI.domain.model.Usuarios;
import com.ArgusAPI.domain.model.Voto;
import com.ArgusAPI.dto.VotoDTO;
import com.ArgusAPI.repository.SessaoVotacaoRepository;
import com.ArgusAPI.repository.UsuarioRepository;
import com.ArgusAPI.repository.VotoRepository;

@Service

public class VotoService {
    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para criar um voto
    public VotoDTO criarVoto(VotoDTO votoDTO) {
        SessaoVotacao sessaoVotacao = (SessaoVotacao) sessaoVotacaoRepository.findByTitulo(votoDTO.sessaoVotacaoNome())
                .orElseThrow(() -> new IllegalArgumentException("Sessão de Votação não encontrada."));
        Usuarios usuario = usuarioRepository.findByNome(votoDTO.usuarioNome())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        Voto voto = new Voto();
        voto.setOpcao(votoDTO.opcao());
        voto.setSessaoVotacao(sessaoVotacao);
        voto.setUsuario(usuario);

        Voto votoSalvo = votoRepository.save(voto);

        return converterParaDTO(votoSalvo);
    }

    public List<VotoDTO> buscarTodosVotos() {
        List<Voto> votos = votoRepository.findAll();
        return votos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<VotoDTO> buscarVotoPorId(Long id) {
        Optional<Voto> votoOpt = votoRepository.findById(id);
        return votoOpt.map(this::converterParaDTO);
    }

    public void deletarVoto(Long id) {
        if (!votoRepository.existsById(id)) {
            throw new IllegalArgumentException("Voto com o ID fornecido não encontrado.");
        }
        votoRepository.deleteById(id);
    }

    // Método auxiliar para converter entidade Voto para VotoDTO
    private VotoDTO converterParaDTO(Voto voto) {
        return new VotoDTO(
                voto.getId(),
                voto.isOpcao(),
                voto.getSessaoVotacao().getTitulo(),
                voto.getUsuario().getNome()
        );
    }
}
