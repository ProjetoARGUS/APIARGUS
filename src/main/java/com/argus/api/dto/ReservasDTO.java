package com.argus.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ReservasDTO(
        Long id,
        String areaNome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataReserva,  

        @JsonFormat(pattern = "HH:mm")
        LocalTime horaInicio,  

        @JsonFormat(pattern = "HH:mm")
        LocalTime horaFim 
) { }
