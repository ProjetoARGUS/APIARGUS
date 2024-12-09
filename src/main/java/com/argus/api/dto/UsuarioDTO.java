package com.argus.api.dto;

public record UsuarioDTO(
        Long id,
        String nome,
        String telefone,
        String tipoDoUsuario,
        String bloco,
        Integer apartamento,
        String condominioNome,
        String condominioEndereco
) { }
