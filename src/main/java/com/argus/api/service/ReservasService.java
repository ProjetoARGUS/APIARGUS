package com.argus.api.service;

import com.argus.api.domain.model.AreasComuns;
import com.argus.api.domain.model.Reservas;
import com.argus.api.dto.ReservasDTO;
import com.argus.api.repository.AreasComunsRepository;
import com.argus.api.repository.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservasService {

    @Autowired
    ReservasRepository reservasRepository;

    @Autowired
    AreasComunsRepository areasComunsRepository;

    public ReservasDTO reservarArea(ReservasDTO reservasDTO) {

        AreasComuns areasComuns = areasComunsRepository.findByNome(reservasDTO.areaNome())
                .orElseThrow(() -> new RuntimeException("Área não encontrada."));

        if (!areasComuns.getDisponivel()) {
            throw new RuntimeException("No momento não está disponível");
        }

        if (reservasRepository.findByAreasComunsAndDataReserva(areasComuns, reservasDTO.dataReserva()).isPresent()) {
            throw new RuntimeException("Área já reservada para essa data.");
        }

        Reservas reservas = new Reservas();
        reservas.setAreasComuns(areasComuns);
        reservas.setDataReserva(reservasDTO.dataReserva());

        reservasRepository.save(reservas);

        return convertToDTO(reservas);
    }

    public List<ReservasDTO> listarTodasReservas() {

        List<Reservas> reservas = reservasRepository.findAll();


        return reservas.stream()
                .map(reserva -> new ReservasDTO(
                        reserva.getId(),
                        reserva.getAreasComuns().getNome(),
                        reserva.getDataReserva()))
                .collect(Collectors.toList());
    }

    public String excluirReserva(Long reservaId) {
        // Verificar se a reserva existe
        Reservas reserva = reservasRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada."));

        reservasRepository.delete(reserva);

        String mensagem = "A reserva da área " + reserva.getAreasComuns().getNome() +
                " para a data " + reserva.getDataReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " foi deletada com sucesso.";

        return mensagem;
    }


    private ReservasDTO convertToDTO(Reservas reservas) {
        return new ReservasDTO(
                reservas.getId(),
                reservas.getAreasComuns().getNome(),
                reservas.getDataReserva()
        );
    }
}