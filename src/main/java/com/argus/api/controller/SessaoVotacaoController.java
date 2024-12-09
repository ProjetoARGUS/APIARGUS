package com.argus.api.controller;

import com.argus.api.dto.SessaoVotacaoDTO;
import com.argus.api.service.SessaoVotacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessaoVotacao")
public class SessaoVotacaoController {

    private final SessaoVotacaoService sessaoVotacaoService;

    public SessaoVotacaoController(SessaoVotacaoService sessaoVotacaoService) {
        this.sessaoVotacaoService = sessaoVotacaoService;
    }

    @PostMapping
    public ResponseEntity<SessaoVotacaoDTO> criarSessaoVotacao(@Valid @RequestBody SessaoVotacaoDTO sessaoVotacaoDTO) {
        SessaoVotacaoDTO resposta = sessaoVotacaoService.criarSessao(sessaoVotacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<SessaoVotacaoDTO>> listarTodasSessoes() {
        List<SessaoVotacaoDTO> sessoes = sessaoVotacaoService.listarTodasSessoes();
        return ResponseEntity.ok(sessoes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SessaoVotacaoDTO> buscarSessaoPorId(@PathVariable Long id) {
        SessaoVotacaoDTO sessao = sessaoVotacaoService.buscarSessaoPorId(id);
        return ResponseEntity.ok(sessao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarSessaoVotacao(@PathVariable Long id) {
        sessaoVotacaoService.deletarSessao(id);
        String mensagem = "Sessão de votação foi deletada com sucesso.";
        return ResponseEntity.ok(mensagem);
    }
}
