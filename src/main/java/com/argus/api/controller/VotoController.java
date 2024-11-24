package com.argus.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voto")
public class VotoController {
    @Autowired
    private VotoService votoService;

    @PostMapping("/criar")
    public ResponseEntity<VotoDTO> criarVoto(@RequestBody VotoDTO votoDTO) {
        VotoDTO novoVoto = votoService.criarVoto(votoDTO);
        return ResponseEntity.ok(novoVoto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<VotoDTO>> buscarTodosVotos() {
        List<VotoDTO> votos = votoService.buscarTodosVotos();
        return ResponseEntity.ok(votos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotoDTO> buscarVotoPorId(@PathVariable Long id) {
        return votoService.buscarVotoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarVoto(@PathVariable Long id) {
        votoService.deletarVoto(id);
        return ResponseEntity.noContent().build();
    }
}
