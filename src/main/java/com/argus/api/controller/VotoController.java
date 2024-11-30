package com.argus.api.controller;

import com.argus.api.dto.VotoDTO;
import com.argus.api.domain.model.Voto;
import com.argus.api.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/votos")
public class VotoController {

    private final VotoService votoService;

    @Autowired
    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping
    public ResponseEntity<String> registrarVoto(@Valid @RequestBody VotoDTO votoDTO, @RequestHeader("usuarioId") Long usuarioId) {
        // Chama o serviço para registrar o voto
        String resposta = votoService.registrarVoto(votoDTO, usuarioId);
        return ResponseEntity.ok(resposta);
    }

    // Endpoint para listar um voto pelo seu id
    @GetMapping("/{Id}")
    public ResponseEntity<List<Voto>> listarVotos(@PathVariable Long sessaoVotacaoId) {
        // Chama o serviço para listar os votos de uma sessão específica
        List<Voto> votos = votoService.listarVotos(sessaoVotacaoId);
        return ResponseEntity.ok(votos);
    }

    // Endpoint para listar todos os votos
    @GetMapping
    public ResponseEntity<List<Voto>> listarTodosVotos() {
        // Chama o serviço para listar todos os votos
        List<Voto> votos = votoService.listarTodosVotos();
        return ResponseEntity.ok(votos);
    }

    // Endpoint para deletar um voto pelo ID
    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deletarVoto(@PathVariable Long votoId) {
        // Chama o serviço para deletar o voto
        String resposta = votoService.deletarVoto(votoId);
        return ResponseEntity.ok(resposta);
    }
}
