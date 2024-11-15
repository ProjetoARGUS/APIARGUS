package com.argus.api.controller;

import com.argus.api.domain.model.Condominio;
import com.argus.api.dto.CondominioDTO;
import com.argus.api.service.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/condominio")
public class CondominioController {
    @Autowired
    private CondominioService condominioService;

    @PostMapping("/criar")
    public ResponseEntity<String> createCondominio(@RequestBody CondominioDTO condominioDTO) {
        condominioService.createCondominio(condominioDTO);
        return ResponseEntity.ok(condominioDTO.nome() + " criado com sucesso!");
    }

    @GetMapping("/listar")
    public List<Condominio> getAllCondominios() {
        return condominioService.getAllCondominios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Condominio> getCondominioById(@PathVariable Long id) throws Exception {
        Condominio condominio = condominioService.getCondominioById(id);
        return ResponseEntity.ok(condominio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Condominio> updateCondominio(@PathVariable Long id, @RequestBody CondominioDTO condominioDTO) {
        Condominio condominio = condominioService.updateCondominio(id, condominioDTO);
        return ResponseEntity.ok(condominio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCondominio(@PathVariable Long id) {
        condominioService.deleteCondominio(id);
        return ResponseEntity.ok("Condomínio " + id + " excluído.");
    }
}
