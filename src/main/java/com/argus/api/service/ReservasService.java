package com.argus.api.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.argus.api.domain.model.AreasComuns;
import com.argus.api.domain.model.Reservas;
import com.argus.api.dto.ReservasDTO;
import com.argus.api.repository.AreasComunsRepository;
import com.argus.api.repository.ReservasRepository;

@Service
public class ReservasService {

    @Autowired
    ReservasRepository reservasRepository;

    @Autowired
    AreasComunsRepository areasComunsRepository;
    
    @Autowired
    private NotificacoesService notificacoesService;

    public ReservasDTO reservarArea(ReservasDTO reservasDTO) {

        AreasComuns areasComuns = areasComunsRepository.findByNome(reservasDTO.areaNome())
                .orElseThrow(() -> new RuntimeException("Área não encontrada."));

        if (!areasComuns.getDisponivel()) {
            throw new RuntimeException("No momento não está disponível");
        }

        if (reservasRepository.findByAreasComunsAndDataReservaAndHoraInicioBetween(
                areasComuns, reservasDTO.dataReserva(), reservasDTO.horaInicio(), reservasDTO.horaFim()).isPresent()) {
            throw new RuntimeException("Área já reservada para essa data e horário.");
        }

        Reservas reservas = new Reservas();
        reservas.setAreasComuns(areasComuns);
        reservas.setDataReserva(reservasDTO.dataReserva());
        reservas.setHoraInicio(reservasDTO.horaInicio());
        reservas.setHoraFim(reservasDTO.horaFim());

        reservasRepository.save(reservas);
        
        notificacoesService.criarNotificacaoReserva(reservas);


        return convertToDTO(reservas);
    }

    public List<ReservasDTO> listarTodasReservas() {
        List<Reservas> reservas = reservasRepository.findAll();

        return reservas.stream()
                .map(reserva -> new ReservasDTO(
                        reserva.getId(),
                        reserva.getAreasComuns().getNome(),
                        reserva.getDataReserva(),
                        reserva.getHoraInicio(),
                        reserva.getHoraFim()))
                .collect(Collectors.toList());
    }

    public String excluirReserva(Long reservaId) {
        // Verificar se a reserva existe
        Reservas reserva = reservasRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada."));

        reservasRepository.delete(reserva);

        String mensagem = "A reserva da área " + reserva.getAreasComuns().getNome() +
                " para a data " + reserva.getDataReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " das " + reserva.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")) +
                " até " + reserva.getHoraFim().format(DateTimeFormatter.ofPattern("HH:mm")) +
                " foi deletada com sucesso.";

        return mensagem;
    }

    private ReservasDTO convertToDTO(Reservas reservas) {
        return new ReservasDTO(
                reservas.getId(),
                reservas.getAreasComuns().getNome(),
                reservas.getDataReserva(),
                reservas.getHoraInicio(),
                reservas.getHoraFim()
        );
    }
}
