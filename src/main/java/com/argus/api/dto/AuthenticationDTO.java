package com.argus.api.dto;

public record AuthenticationDTO(
        String cpf,
        String password
) { }
