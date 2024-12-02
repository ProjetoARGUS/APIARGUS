package com.argus.api.dto;

import jakarta.validation.constraints.NotNull;

public record VotoDTO(
        @NotNull(message = "O ID da sessão é obrigatório.")
        Long sessaoId,

        @NotNull(message = "O voto é obrigatório.")
        Boolean voto
) {}


