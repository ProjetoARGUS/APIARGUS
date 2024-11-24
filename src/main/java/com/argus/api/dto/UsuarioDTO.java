package com.argus.api.dto;

public record UsuarioDTO(
        Long id,
        String nome,
        String telefone,
        String tipoDoUsuario,
        Character bloco,
        Integer apartamento,
        String condominioNome,
        String condominioEndereco
) { }
