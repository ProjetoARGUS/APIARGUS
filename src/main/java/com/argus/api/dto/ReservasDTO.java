package com.argus.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ReservasDTO(
        Long id,
        String areaNome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataReserva
) { }
