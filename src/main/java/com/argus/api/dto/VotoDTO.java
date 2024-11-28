package com.argus.api.dto;

import jakarta.validation.constraints.NotBlank;

public record VotoDTO(
        @NotBlank(message = "O ID da sessão é obrigatório.")
        Long sessaoId,

        @NotBlank(message = "O voto é obrigatório.")
        Boolean voto
) {}


