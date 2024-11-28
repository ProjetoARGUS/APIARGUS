package com.argus.api.repository;

import com.argus.api.domain.model.AreasComuns;
import com.argus.api.domain.model.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface ReservasRepository extends JpaRepository<Reservas, Long> {
    Optional<Reservas> findByAreasComunsAndDataReserva(AreasComuns areasComuns, LocalDate dataReserva);

	Optional<Reservas> findByAreasComunsAndDataReservaAndHoraInicioBetween(AreasComuns areasComuns,
			LocalDate localDate, LocalTime horaInicio, LocalTime horaFim);
}
