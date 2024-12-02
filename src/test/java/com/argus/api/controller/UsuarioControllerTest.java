package com.argus.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import com.argus.api.domain.TipoDoUsuario;
import com.argus.api.domain.model.Condominio;
import com.argus.api.domain.model.Usuarios;
import com.argus.api.dto.UsuarioDTO;
import com.argus.api.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuariosController usuariosController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Usuarios criarUsuarioExemplo() {
        Condominio condominio = new Condominio(
                1L,
                "Condomínio Alpha",
                "Rua X, 123",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return new Usuarios(
                1L,
                "João Silva",
                "12345678900",
                "senha123",
                "123456789",
                TipoDoUsuario.MORADOR,
                'A',
                101,
                condominio,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuariosController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getUsers_DeveRetornarListaDeUsuarios_QuandoUsuariosExistem() throws Exception {
        // Arrange
        Usuarios usuario = criarUsuarioExemplo();
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getTipoDoUsuario().name(),
                usuario.getBloco(),
                usuario.getApartamento(),
                usuario.getCondominio().getNome(),
                usuario.getCondominio().getEndereco()
        );

        List<UsuarioDTO> usuarios = Collections.singletonList(usuarioDTO);

        // Configurar mock
        when(usuarioService.getAllUsers()).thenReturn(usuarios);

        // Act & Assert
        mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(usuario.getId()))
                .andExpect(jsonPath("$[0].nome").value(usuario.getNome()));

        // Verificar chamada do serviço
        verify(usuarioService, times(1)).getAllUsers();
    }

    @Test
    void getUsers_DeveRetornarListaVazia_QuandoNaoHaUsuarios() throws Exception {
        // Arrange
        when(usuarioService.getAllUsers()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(usuarioService, times(1)).getAllUsers();
    }

    @Test
    void findUserById_DeveRetornarUsuario_QuandoUsuarioExiste() throws Exception {
        // Arrange
        Usuarios usuario = criarUsuarioExemplo();
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getTipoDoUsuario().name(),
                usuario.getBloco(),
                usuario.getApartamento(),
                usuario.getCondominio().getNome(),
                usuario.getCondominio().getEndereco()
        );

        when(usuarioService.findUserById(usuario.getId()))
                .thenReturn(Optional.of(usuarioDTO));

        // Act & Assert
        mockMvc.perform(get("/usuarios/{id}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuario.getId()))
                .andExpect(jsonPath("$.nome").value(usuario.getNome()));

        verify(usuarioService, times(1)).findUserById(usuario.getId());
    }

    @Test
    void findUserById_DeveRetornarNotFound_QuandoUsuarioNaoExiste() throws Exception {
        // Arrange
        Long idInexistente = 999L;
        when(usuarioService.findUserById(idInexistente))
                .thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/usuarios/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(usuarioService, times(1)).findUserById(idInexistente);
    }

    @Test
    void updateUser_DeveAtualizarUsuario_QuandoDadosValidos() throws Exception {
        // Arrange
        Usuarios usuarioOriginal = criarUsuarioExemplo();
        Usuarios usuarioAtualizado = new Usuarios(
                usuarioOriginal.getId(),
                "João Silva Atualizado",
                usuarioOriginal.getCpf(),
                "novaSenha123",
                "987654321",
                usuarioOriginal.getTipoDoUsuario(),
                usuarioOriginal.getBloco(),
                usuarioOriginal.getApartamento(),
                usuarioOriginal.getCondominio(),
                usuarioOriginal.getCreatedAt(),
                LocalDateTime.now()
        );

        UsuarioDTO usuarioDTOAtualizado = new UsuarioDTO(
                usuarioAtualizado.getId(),
                usuarioAtualizado.getNome(),
                usuarioAtualizado.getTelefone(),
                usuarioAtualizado.getTipoDoUsuario().name(),
                usuarioAtualizado.getBloco(),
                usuarioAtualizado.getApartamento(),
                usuarioAtualizado.getCondominio().getNome(),
                usuarioAtualizado.getCondominio().getEndereco()
        );

        when(usuarioService.updateUser(eq(usuarioOriginal.getId()), any(Usuarios.class)))
                .thenReturn(usuarioAtualizado);
        when(usuarioService.convertToDTO(any(Usuarios.class)))
                .thenReturn(usuarioDTOAtualizado);

        // Act & Assert
        mockMvc.perform(put("/usuarios/{id}", usuarioOriginal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva Atualizado"))
                .andExpect(jsonPath("$.telefone").value("987654321"));

        verify(usuarioService, times(1)).updateUser(eq(usuarioOriginal.getId()), any(Usuarios.class));
        verify(usuarioService, times(1)).convertToDTO(any(Usuarios.class));
    }

    @Test
    void deleteUser_DeveExcluirUsuario_QuandoUsuarioExiste() throws Exception {
        // Arrange
        Usuarios usuario = criarUsuarioExemplo();
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getTipoDoUsuario().name(),
                usuario.getBloco(),
                usuario.getApartamento(),
                usuario.getCondominio().getNome(),
                usuario.getCondominio().getEndereco()
        );

        when(usuarioService.deleteUser(usuario.getId())).thenReturn(usuarioDTO);

        // Act & Assert
        mockMvc.perform(delete("/usuarios/{id}", usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(usuario.getNome() + " Foi Deletado Com Sucesso!"));

        verify(usuarioService, times(1)).deleteUser(usuario.getId());
    }

    @Test
    void updateUser_DeveLancarExcecao_QuandoDadosInvalidos() throws Exception {
        // Arrange
        Long id = 1L;
        Usuarios usuarioInvalido = new Usuarios();

        // Act & Assert
        mockMvc.perform(put("/usuarios/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioInvalido)))
                .andExpect(status().isBadRequest());
    }
}