package com.argus.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

import com.argus.api.domain.model.SessaoVotacao;
import com.argus.api.domain.model.Usuarios;
import com.argus.api.domain.model.Voto;
import com.argus.api.dto.VotoDTO;
import com.argus.api.service.VotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class VotoControllerTest {

    private MockMvc mockMvc;
    @Mock
    private VotoService votoService;
    @InjectMocks
    private VotoController votoController;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(votoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testRegistrarVoto_Sucesso() throws Exception {
        VotoDTO votoDTO = new VotoDTO(1L, true);
        Long usuarioId = 1L;
        String expectedResponse = "Voto registrado com sucesso.";

        when(votoService.registrarVoto(votoDTO, usuarioId)).thenReturn(expectedResponse);

        mockMvc.perform(post("/votos")
                        .header("usuarioId", usuarioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        verify(votoService).registrarVoto(votoDTO, usuarioId);
    }

    @Test
    public void testListarVotos_PorSessao() throws Exception {
        Long sessaoVotacaoId = 1L;
        Voto voto1 = criarVotoMock(1L, true);
        Voto voto2 = criarVotoMock(2L, false);
        List<Voto> votos = Arrays.asList(voto1, voto2);

        when(votoService.listarVotos(sessaoVotacaoId)).thenReturn(votos);

        mockMvc.perform(get("/votos/{Id}", sessaoVotacaoId)) // 'Id' com maiúscula aqui
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].voto", is(true)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].voto", is(false)));


        verify(votoService).listarVotos(sessaoVotacaoId);
    }

    @Test
    public void testListarTodosVotos() throws Exception {
        Voto voto1 = new Voto();
        Voto voto2 = new Voto();
        List<Voto> votos = Arrays.asList(voto1, voto2);

        when(votoService.listarTodosVotos()).thenReturn(votos);

        mockMvc.perform(get("/votos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));

        verify(votoService).listarTodosVotos();
    }

    @Test
    public void testDeletarVoto_Sucesso() throws Exception {
        Long votoId = 1L;
        String expectedResponse = "Voto deletado com sucesso.";

        when(votoService.deletarVoto(votoId)).thenReturn(expectedResponse);

        mockMvc.perform(delete("/votos/{Id}", votoId)) // 'Id' com maiúscula aqui
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        verify(votoService).deletarVoto(votoId);
    }

    @Test
    public void testRegistrarVoto_Falha_SemUsuarioId() throws Exception {
        VotoDTO votoDTO = new VotoDTO(1L, true);

        mockMvc.perform(post("/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoDTO)))
                .andExpect(status().isBadRequest());
    }


    private Voto criarVotoMock(Long id, Boolean votoValor) {
        Voto voto = new Voto();
        voto.setId(id);
        voto.setVoto(votoValor);
        voto.setSessaoVotacao(new SessaoVotacao());
        voto.setUsuarios(new Usuarios());
        return voto;
    }
}