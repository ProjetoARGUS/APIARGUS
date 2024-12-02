package com.argus.api.controller;

import com.argus.api.domain.model.Condominio;
import com.argus.api.dto.CondominioDTO;
import com.argus.api.service.CondominioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CondominioController.class)
public class CondominioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CondominioService condominioService;


    private CondominioDTO validCondominioDTO;
    private CondominioDTO invalidCondominioDTO;
    private Condominio condominio;

    @BeforeEach
    public void setup() {
        validCondominioDTO = new CondominioDTO("Condomínio Teste", "Rua Exemplo, 123");
        invalidCondominioDTO = new CondominioDTO("", "");

        condominio = new Condominio();
        condominio.setId(1L);
        condominio.setNome("Condomínio Teste");
        condominio.setEndereco("Rua Exemplo, 123");
    }

    @Test
    public void testCreateCondominio() throws Exception {
        when(condominioService.createCondominio(any(CondominioDTO.class))).thenReturn(condominio);

        mockMvc.perform(post("/condominio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper().writeValueAsString(validCondominioDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Condomínio Teste criado com sucesso!")));

        verify(condominioService, times(1)).createCondominio(any(CondominioDTO.class));
    }

    @Test
    public void testCreateCondominio_InvalidInput() throws Exception {
        mockMvc.perform(post("/condominio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper().writeValueAsString(invalidCondominioDTO)))
                .andExpect(status().isBadRequest()); //Verifica apenas o status

        verify(condominioService, never()).createCondominio(any(CondominioDTO.class));
    }

    @Test
    public void testGetAllCondominios() throws Exception {
        List<Condominio> condominios = Arrays.asList(condominio);
        when(condominioService.getAllCondominios()).thenReturn(condominios);

        mockMvc.perform(get("/condominio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Condomínio Teste"))
                .andExpect(jsonPath("$[0].endereco").value("Rua Exemplo, 123"));

        verify(condominioService, times(1)).getAllCondominios();
    }

    @Test
    public void testGetCondominioById() throws Exception {
        when(condominioService.getCondominioById(eq(1L))).thenReturn(condominio);

        mockMvc.perform(get("/condominio/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Condomínio Teste"))
                .andExpect(jsonPath("$.endereco").value("Rua Exemplo, 123"));

        verify(condominioService, times(1)).getCondominioById(eq(1L));
    }

    @Test
    public void testUpdateCondominio() throws Exception {
        when(condominioService.updateCondominio(eq(1L), any(CondominioDTO.class))).thenReturn(condominio);

        mockMvc.perform(put("/condominio/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper().writeValueAsString(validCondominioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Condomínio Teste"))
                .andExpect(jsonPath("$.endereco").value("Rua Exemplo, 123"));

        verify(condominioService, times(1)).updateCondominio(eq(1L), any(CondominioDTO.class));
    }

    @Test
    public void testDeleteCondominio() throws Exception {
        doNothing().when(condominioService).deleteCondominio(eq(1L));

        mockMvc.perform(delete("/condominio/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Condomínio 1 excluído.")));

        verify(condominioService, times(1)).deleteCondominio(eq(1L));
    }

    private ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}