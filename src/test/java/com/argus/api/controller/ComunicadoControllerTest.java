package com.argus.api.controller;

import com.argus.api.controller.ComunicadoController;
import com.argus.api.dto.ComunicadoDTO;
import com.argus.api.service.ComunicadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ComunicadoControllerTest {

    @Mock
    private ComunicadoService comunicadoService;

    @InjectMocks
    private ComunicadoController comunicadoController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(comunicadoController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void enviarComunicado_DeveRetornarComunicadoCriado() throws Exception {
        // Arrange
        ComunicadoDTO comunicadoDTO = new ComunicadoDTO(1L, "Condomínio Alpha", "Título de Teste", "Mensagem de teste");
        when(comunicadoService.enviarComunicado(any(ComunicadoDTO.class))).thenReturn(comunicadoDTO);

        // Act & Assert
        mockMvc.perform(post("/comunicado") // Note a remoção do /enviar
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comunicadoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.condominioNome").value("Condomínio Alpha"))
                .andExpect(jsonPath("$.titulo").value("Título de Teste"))
                .andExpect(jsonPath("$.mensagem").value("Mensagem de teste"));

        verify(comunicadoService, times(1)).enviarComunicado(any(ComunicadoDTO.class));
    }

    @Test
    void listarComunicados_DeveRetornarListaDeComunicados() throws Exception {
        // Arrange
        ComunicadoDTO comunicado1 = new ComunicadoDTO(1L, "Condomínio Alpha", "Título 1", "Mensagem 1");
        ComunicadoDTO comunicado2 = new ComunicadoDTO(2L, "Condomínio Beta", "Título 2", "Mensagem 2");
        when(comunicadoService.listarComunicados()).thenReturn(Arrays.asList(comunicado1, comunicado2));

        // Act & Assert
        mockMvc.perform(get("/comunicado/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].titulo").value("Título 1"))
                .andExpect(jsonPath("$[0].mensagem").value("Mensagem 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].titulo").value("Título 2"))
                .andExpect(jsonPath("$[1].mensagem").value("Mensagem 2"));

        verify(comunicadoService, times(1)).listarComunicados();
    }

    @Test
    void atualizarComunicado_DeveRetornarComunicadoAtualizado() throws Exception {
        // Arrange
        Long id = 1L;
        String novoTitulo = "Novo Título";
        String novaMensagem = "Mensagem atualizada";

        ComunicadoDTO comunicadoAtualizado = new ComunicadoDTO(1L, "Condomínio Alpha", novoTitulo, novaMensagem);
        when(comunicadoService.atualizarComunicado(eq(id), eq(novaMensagem), eq(novoTitulo))).thenReturn(comunicadoAtualizado);

        // Prepare o payload (sem alterações)
        Map<String, String> payload = new HashMap<>();
        payload.put("mensagem", novaMensagem);
        payload.put("titulo", novoTitulo);


        // Act & Assert
        mockMvc.perform(put("/comunicado/{id}", id)  // Note a remoção do /atualizar
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value(novoTitulo))
                .andExpect(jsonPath("$.mensagem").value(novaMensagem));

        verify(comunicadoService, times(1)).atualizarComunicado(eq(id), eq(novaMensagem), eq(novoTitulo));
    }

    @Test
    void excluirComunicado_DeveRetornarMensagemDeSucesso() throws Exception {
        // Arrange
        Long id = 1L;

        // Act & Assert
        mockMvc.perform(delete("/comunicado/{id}", id)) // Note a remoção do /excluir
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Comunicado excluído com sucesso."));

        verify(comunicadoService, times(1)).excluirComunicado(eq(id));
    }

    @Test
    void excluirComunicado_DeveRetornarErroQuandoNaoEncontrado() throws Exception {
        // Arrange
        Long id = 1L;
        doThrow(new RuntimeException("Comunicado não encontrado.")).when(comunicadoService).excluirComunicado(eq(id));

        // Act & Assert
        mockMvc.perform(delete("/comunicado/{id}", id)) // Note a remoção do /excluir
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("Comunicado não encontrado."));

        verify(comunicadoService, times(1)).excluirComunicado(eq(id));
    }
}
