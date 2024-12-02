package com.argus.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.argus.api.domain.model.Ocorrencias;
import com.argus.api.dto.OcorrenciasDTO;
import com.argus.api.service.OcorrenciasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OcorrenciasControllerTest {
    @Mock
    private OcorrenciasService ocorrenciasService;

    @InjectMocks
    private OcorrenciasController ocorrenciasController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(ocorrenciasController).build();
    }

    // Método auxiliar para criar OcorrenciasDTO para testes
    private OcorrenciasDTO criarOcorrenciaDTO(Long id, Long idUsuario, Long idArea) {
        OcorrenciasDTO dto = new OcorrenciasDTO();
        dto.setId(id);
        dto.setTitulo("Ocorrência de Teste " + id);
        dto.setDescricao("Descrição da Ocorrência " + id);
        dto.setTipo(Ocorrencias.TipoOcorrencia.SOLICITACAO_DE_MANUTENCAO);
        dto.setStatusAprovacao(Ocorrencias.StatusAprovacao.AGUARDANDO);
        dto.setStatusResolucao(Ocorrencias.StatusResolucao.PENDENTE);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setIdUsuario(idUsuario);
        dto.setIdArea(idArea);
        return dto;
    }

    @Test
    void criarOcorrencia_DeveRetornarOcorrenciaCriada() throws Exception {
        OcorrenciasDTO ocorrenciaDTO = criarOcorrenciaDTO(1L, 1L, 1L);

        // Use when() with the specific DTO instead of any()
        when(ocorrenciasService.criarOcorrencia(ocorrenciaDTO)).thenReturn(ocorrenciaDTO);

        mockMvc.perform(post("/ocorrencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ocorrenciaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Ocorrência de Teste 1"));

        // Verify with the specific DTO
        verify(ocorrenciasService).criarOcorrencia(ocorrenciaDTO);
    }

    @Test
    void listarOcorrencias_DeveRetornarListaDeOcorrencias() throws Exception {
        List<OcorrenciasDTO> ocorrencias = Arrays.asList(
                criarOcorrenciaDTO(1L, 1L, 1L),
                criarOcorrenciaDTO(2L, 2L, 2L)
        );

        when(ocorrenciasService.listarTodasOcorrencias()).thenReturn(ocorrencias);

        mockMvc.perform(get("/ocorrencias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].titulo").value("Ocorrência de Teste 1"))
                .andExpect(jsonPath("$[0].idUsuario").value(1L))
                .andExpect(jsonPath("$[0].idArea").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].titulo").value("Ocorrência de Teste 2"))
                .andExpect(jsonPath("$[1].idUsuario").value(2L))
                .andExpect(jsonPath("$[1].idArea").value(2L));

        verify(ocorrenciasService).listarTodasOcorrencias();
    }

    @Test
    void buscarOcorrencia_DeveRetornarOcorrenciaQuandoExistir() throws Exception {
        Long id = 1L;
        OcorrenciasDTO ocorrenciaDTO = criarOcorrenciaDTO(id, 1L, 1L);

        when(ocorrenciasService.buscarOcorrenciaPorId(id)).thenReturn(ocorrenciaDTO);

        mockMvc.perform(get("/ocorrencias/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Ocorrência de Teste 1"));

        verify(ocorrenciasService).buscarOcorrenciaPorId(id);
    }

    @Test
    void atualizarOcorrencia_DeveRetornarOcorrenciaAtualizada() throws Exception {
        Long id = 1L;
        OcorrenciasDTO ocorrenciaDTO = criarOcorrenciaDTO(id, 1L, 1L);
        ocorrenciaDTO.setTitulo("Ocorrência Atualizada");

        when(ocorrenciasService.atualizarOcorrencia(id, ocorrenciaDTO)).thenReturn(ocorrenciaDTO);

        mockMvc.perform(put("/ocorrencias/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ocorrenciaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Ocorrência Atualizada"));

        verify(ocorrenciasService).atualizarOcorrencia(id, ocorrenciaDTO);
    }

    @Test
    void deletarOcorrencia_DeveRetornarNoContent() throws Exception {
        Long id = 1L;
        doNothing().when(ocorrenciasService).deletarOcorrencia(id);

        mockMvc.perform(delete("/ocorrencias/{id}", id))
                .andExpect(status().isNoContent());

        verify(ocorrenciasService).deletarOcorrencia(id);
    }

}