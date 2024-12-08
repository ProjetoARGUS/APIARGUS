package com.argus.api.dto;

import com.argus.api.domain.model.Ocorrencias;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OcorrenciasDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Ocorrencias.TipoOcorrencia tipo;
    private Ocorrencias.StatusAprovacao statusAprovacao;
    private Ocorrencias.StatusResolucao statusResolucao;
    private LocalDateTime dataCriacao;
    private Long idUsuario;
    private Long idArea;
    private String feedback;
}