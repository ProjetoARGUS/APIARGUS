package com.argus.api.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.argus.api.dto.AreasComunsDTO;
import com.argus.api.service.AreasComunsService;

@RestController
@RequestMapping("/areasComuns")
public class AreasComunsController {

    @Autowired
    private AreasComunsService areasComunsService;

    @PostMapping("/cadastrar")
    public ResponseEntity<AreasComunsDTO> cadastrarAreaComum(@RequestBody AreasComunsDTO areaComumDTO) {
        AreasComunsDTO areaCadastrada = areasComunsService.cadastrarAreaComum(areaComumDTO);
        return new ResponseEntity<>(areaCadastrada, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<AreasComunsDTO>> listarTodasAsAreasComuns() {
        List<AreasComunsDTO> areasComuns = areasComunsService.listarTodasAsAreasComuns();
        return ResponseEntity.ok(areasComuns);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AreasComunsDTO> atualizarAreaComum(@PathVariable Long id, @RequestBody AreasComunsDTO areasComunsDTO) {
        AreasComunsDTO areaAtualizada = areasComunsService.atualizarAreaComum(id, areasComunsDTO);
        return ResponseEntity.ok(areaAtualizada);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirAreaComum(@PathVariable Long id) {
        return areasComunsService.excluirAreaComum(id);
    }
}
