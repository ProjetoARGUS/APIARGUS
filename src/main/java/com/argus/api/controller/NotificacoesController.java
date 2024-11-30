package com.argus.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.argus.api.dto.NotificacaoDTO;
import com.argus.api.service.NotificacoesService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/notificacoes")
public class NotificacoesController {

	@Autowired
	private NotificacoesService notificacoesService;


	@GetMapping
	public List<NotificacaoDTO> buscarUltimasNotificacoes() {
		return notificacoesService.buscarUltimasNotificacoes();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarNotificacao(@PathVariable Long id) {
		if (notificacoesService.deletarNotificacao(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
