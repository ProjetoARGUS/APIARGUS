package com.argus.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.argus.api.domain.model.SessaoVotacao;
import com.argus.api.domain.model.Usuarios;
import com.argus.api.domain.model.Voto;
import com.argus.api.dto.VotoDTO;
import com.argus.api.repository.SessaoVotacaoRepository;
import com.argus.api.repository.UsuarioRepository;
import com.argus.api.repository.VotoRepository;

@Service
public class VotoService {

    private final VotoRepository votoRepository;
    private final SessaoVotacaoRepository sessaoVotacaoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public VotoService(VotoRepository votoRepository, SessaoVotacaoRepository sessaoVotacaoRepository, UsuarioRepository usuarioRepository) {
        this.votoRepository = votoRepository;
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public String registrarVoto(VotoDTO votoDTO, Long usuarioId) {
        // Obter o usuário autenticado pelo ID
        Usuarios usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // Verificar se a sessão de votação existe
        SessaoVotacao sessao = sessaoVotacaoRepository.findById(votoDTO.sessaoId())
                .orElseThrow(() -> new IllegalArgumentException("Sessão de votação não encontrada."));

        // Verificar se o usuário já votou nesta sessão
        if (votoRepository.existsByUsuariosAndSessaoVotacao(usuario, sessao)) {
            throw new IllegalArgumentException("Usuário já votou nesta sessão.");
        }

        // Converter o VotoDTO para Voto (entidade)
        Voto novoVoto = converterParaVoto(votoDTO, sessao, usuario);

        // Registrar o voto
        votoRepository.save(novoVoto);

        return "Voto registrado com sucesso.";
    }

    // Método para listar todos os votos de uma sessão de votação
    public List<Voto> listarVotos(Long sessaoVotacaoId) {
        SessaoVotacao sessaoVotacao = sessaoVotacaoRepository.findById(sessaoVotacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Sessão de votação não encontrada."));

        return votoRepository.findBySessaoVotacao(sessaoVotacao);
    }

    // Novo método para listar todos os votos
    public List<Voto> listarTodosVotos() {
        return votoRepository.findAll();  // Retorna todos os votos
    }

    // Método para deletar um voto pelo ID
    public String deletarVoto(Long votoId) {
        Voto voto = votoRepository.findById(votoId)
                .orElseThrow(() -> new IllegalArgumentException("Voto não encontrado."));

        votoRepository.delete(voto);
        return "Voto deletado com sucesso.";
    }

    // Método auxiliar para converter VotoDTO para Voto
    private Voto converterParaVoto(VotoDTO votoDTO, SessaoVotacao sessao, Usuarios usuario) {
        Voto voto = new Voto();
        voto.setVoto(votoDTO.voto());
        voto.setSessaoVotacao(sessao);
        voto.setUsuarios(usuario);
        return voto;
    }
}
