package com.argus.api.dto;

import java.time.LocalDateTime;

public record VotoDTO(
        Long id,
        boolean opcao,
        String sessaoVotacaoNome,
        String usuarioNome
) {}