package com.argus.api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record NotificacaoDTO(
        String titulo,
        String corpoDeTexto
        
) {}
