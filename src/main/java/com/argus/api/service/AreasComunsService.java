package com.argus.api.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.argus.api.domain.model.AreasComuns;
import com.argus.api.domain.model.Condominio;
import com.argus.api.dto.AreasComunsDTO;
import com.argus.api.repository.AreasComunsRepository;
import com.argus.api.repository.CondominioRepository;

import jakarta.transaction.Transactional;

@Service
public class AreasComunsService {

    @Autowired
    private AreasComunsRepository areasComunsRepository;

    @Autowired
    private CondominioRepository condominioRepository;

    public AreasComunsDTO cadastrarAreaComum(AreasComunsDTO areasComunsDTO) {
        // Buscar condomínio pelo nome
        Condominio condominio = condominioRepository.findByNome(areasComunsDTO.condominioNome())
                .orElseThrow(() -> new RuntimeException("Condomínio não encontrado."));

        AreasComuns areasComuns = new AreasComuns();
        areasComuns.setNome(areasComunsDTO.nome());
        areasComuns.setDisponivel(areasComunsDTO.disponivel());
        areasComuns.setCondominio(condominio);

        areasComunsRepository.save(areasComuns);

        return convertToDTO(areasComuns);
    }

    public List<AreasComunsDTO> listarTodasAsAreasComuns() {
        List<AreasComuns> areasComunsList = areasComunsRepository.findAll();

        return areasComunsList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AreasComunsDTO atualizarAreaComum(Long id, AreasComunsDTO areasComunsDTO) {
        AreasComuns areasComuns = areasComunsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área comum não encontrada."));

        if (areasComunsDTO.nome() != null) {
            areasComuns.setNome(areasComunsDTO.nome());
        }

        if (areasComunsDTO.disponivel() != null) {
            areasComuns.setDisponivel(areasComunsDTO.disponivel());
        }

        areasComunsRepository.save(areasComuns);

        return convertToDTO(areasComuns);
    }


    @Transactional
    public ResponseEntity<String> excluirAreaComum(Long id) {

        AreasComuns areasComuns = areasComunsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área comum não encontrada."));

        String nomeAreaComum = areasComuns.getNome();

        areasComunsRepository.delete(areasComuns);

        return ResponseEntity.ok("A Área de " + nomeAreaComum + " Foi excluída com sucesso.");
    }

    private AreasComunsDTO convertToDTO(AreasComuns areasComuns) {
        return new AreasComunsDTO(
                areasComuns.getId(),
                areasComuns.getNome(),
                areasComuns.getDisponivel(),
                areasComuns.getCondominio().getNome()
        );
    }

}
