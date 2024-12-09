package com.argus.api.service;

import com.argus.api.domain.model.Ocorrencias;
import com.argus.api.domain.model.Usuarios;
import com.argus.api.dto.OcorrenciasDTO;
import com.argus.api.repository.OcorrenciasRepository;
import com.argus.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OcorrenciasService {
    private final OcorrenciasRepository ocorrenciasRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public OcorrenciasDTO criarOcorrencia(OcorrenciasDTO ocorrenciaDTO) {
        Ocorrencias ocorrencia = new Ocorrencias();
        ocorrencia.setTitulo(ocorrenciaDTO.getTitulo());
        ocorrencia.setDescricao(ocorrenciaDTO.getDescricao());
        ocorrencia.setTipo(ocorrenciaDTO.getTipo());
        ocorrencia.setDataCriacao(LocalDateTime.now());
        ocorrencia.setFeedback(ocorrenciaDTO.getFeedback());

        Usuarios usuario = usuarioRepository.findById(ocorrenciaDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        ocorrencia.setUsuario(usuario);

        Ocorrencias salva = ocorrenciasRepository.save(ocorrencia);
        return converterParaDTO(salva);
    }

    public List<OcorrenciasDTO> listarTodasOcorrencias() {
        return ocorrenciasRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public OcorrenciasDTO buscarOcorrenciaPorId(Long id) {
        Ocorrencias ocorrencia = ocorrenciasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));
        return converterParaDTO(ocorrencia);
    }

    @Transactional
    public OcorrenciasDTO atualizarOcorrencia(Long id, OcorrenciasDTO ocorrenciaDTO) {
        Ocorrencias ocorrenciaExistente = ocorrenciasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        ocorrenciaExistente.setTitulo(ocorrenciaDTO.getTitulo());
        ocorrenciaExistente.setDescricao(ocorrenciaDTO.getDescricao());
        ocorrenciaExistente.setTipo(ocorrenciaDTO.getTipo());
        ocorrenciaExistente.setStatusAprovacao(ocorrenciaDTO.getStatusAprovacao());
        ocorrenciaExistente.setStatusResolucao(ocorrenciaDTO.getStatusResolucao());
        ocorrenciaExistente.setFeedback(ocorrenciaDTO.getFeedback());

        Ocorrencias atualizada = ocorrenciasRepository.save(ocorrenciaExistente);
        return converterParaDTO(atualizada);
    }

    @Transactional
    public void deletarOcorrencia(Long id) {
        ocorrenciasRepository.deleteById(id);
    }

    private OcorrenciasDTO converterParaDTO(Ocorrencias ocorrencia) {
        OcorrenciasDTO dto = new OcorrenciasDTO();
        dto.setId(ocorrencia.getId());
        dto.setTitulo(ocorrencia.getTitulo());
        dto.setDescricao(ocorrencia.getDescricao());
        dto.setTipo(ocorrencia.getTipo());
        dto.setStatusAprovacao(ocorrencia.getStatusAprovacao());
        dto.setStatusResolucao(ocorrencia.getStatusResolucao());
        dto.setDataCriacao(ocorrencia.getDataCriacao());
        dto.setIdUsuario(ocorrencia.getUsuario().getId());
        dto.setIdArea(ocorrencia.getArea() != null ? ocorrencia.getArea().getId() : null);
        dto.setFeedback(ocorrencia.getFeedback());
        return dto;
    }
}
