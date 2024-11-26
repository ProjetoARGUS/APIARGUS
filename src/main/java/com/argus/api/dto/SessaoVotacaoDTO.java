package com.argus.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record SessaoVotacaoDTO(
        Long id,

        @NotBlank(message = "A proposta é obrigatória.")
        String proposta,

        @NotBlank(message = "A descrição é obrigatória.")
        String descricao,

        @NotBlank(message = "A data de início é obrigatória.")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataInicio,

        @NotBlank(message = "A data de fim é obrigatória.")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataFim,

        @NotBlank(message = "O nome do condomínio é obrigatório.")
        String condominioNome
) {}


