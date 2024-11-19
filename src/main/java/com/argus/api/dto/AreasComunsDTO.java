package com.argus.api.dto;

public record AreasComunsDTO (
        Long id,
        String nome,
        Boolean disponivel,
        String condominioNome
) {}
