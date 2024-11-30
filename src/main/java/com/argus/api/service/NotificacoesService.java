package com.argus.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.argus.api.domain.model.Comunicados;
import com.argus.api.domain.model.Notificacoes;
import com.argus.api.domain.model.Reservas;
import com.argus.api.dto.NotificacaoDTO;
import com.argus.api.repository.NotificacaoRepository;
import com.argus.api.repository.UsuarioRepository;

@Service
public class NotificacoesService {

	@Autowired
	private NotificacaoRepository notificacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public NotificacaoDTO criarNotificacaoComunicado(Comunicados comunicado) {
		Notificacoes notificacao = new Notificacoes();
		notificacao.setTitulo("Novo Comunicado: " + comunicado.getTitulo());
		notificacao.setCorpoDeTexto("Um novo comunicado foi publicado: " + comunicado.getMensagem());
		notificacao.setComunicado(comunicado); 

		Notificacoes novaNotificacao = notificacaoRepository.save(notificacao);
		System.out.println("[DEBUG] [criarNotificacaoTodos] Notificação criada e associada ao comunicado ID: " + comunicado.getId());

		return toDTO(novaNotificacao);
	}

	public NotificacaoDTO criarNotificacaoReserva(Reservas reserva) {
	    Notificacoes notificacao = new Notificacoes();
	    notificacao.setTitulo("Nova Reserva: " + reserva.getAreasComuns().getNome());
	    notificacao.setCorpoDeTexto(
	        "Reserva para a área " + reserva.getAreasComuns().getNome() + 
	        " em " + reserva.getDataReserva() + 
	        " das " + reserva.getHoraInicio() + 
	        " até " + reserva.getHoraFim()
	    );
	    notificacao.setReserva(reserva);

	    // Salva a notificação no banco
	    Notificacoes novaNotificacao = notificacaoRepository.save(notificacao);
	    System.out.println("[DEBUG] [criarNotificacaoReserva] Notificação criada para a reserva ID: " + reserva.getId());

	    return toDTO(novaNotificacao);
	}




	public List<NotificacaoDTO> buscarUltimasNotificacoes() {
		List<Notificacoes> notificacoes = notificacaoRepository.findTop10ByOrderByIdDesc();
		List<NotificacaoDTO> notificacoesDTO = new ArrayList<>();

		for (Notificacoes notificacao : notificacoes) {
			notificacoesDTO.add(toDTO(notificacao));
		}

		return notificacoesDTO;
	}

	public boolean deletarNotificacao(Long id) {
		if (notificacaoRepository.existsById(id)) {
			notificacaoRepository.deleteById(id);
			return true;  
		} else {
			return false; 
		}
	}

	public NotificacaoDTO toDTO(Notificacoes notificacao) {
		return new NotificacaoDTO(
				notificacao.getTitulo(),
				notificacao.getCorpoDeTexto()		);
	}
}
