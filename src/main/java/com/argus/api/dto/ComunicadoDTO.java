package com.argus.api.dto;

public record ComunicadoDTO(
        Long id,
        String condominioNome,
        String titulo,  
        String mensagem
) { }
