package com.argus.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.argus.api.domain.TipoDoUsuario;
import com.argus.api.domain.model.Condominio;
import com.argus.api.domain.model.Usuarios;
import com.argus.api.dto.AuthenticationDTO;
import com.argus.api.dto.UsuarioDTO;
import com.argus.api.infra.security.TokenService;
import com.argus.api.repository.UsuarioRepository;
import com.argus.api.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void login_ValidCredentials_ShouldReturnToken() throws Exception {
        // Arrange
        String cpf = "12345678900";
        String password = "senha123";
        String expectedToken = "mocked-jwt-token";

        // Criar um usuário mock para autenticação
        Usuarios usuario = new Usuarios();
        usuario.setCpf(cpf);
        usuario.setSenha(password);
        usuario.setTipoDoUsuario(TipoDoUsuario.MORADOR);

        // Preparar o DTO de autenticação
        AuthenticationDTO authDTO = new AuthenticationDTO(cpf, password);

        // Criar mock de autenticação
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(usuario);

        // Configurar os mocks
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);
        when(tokenService.generateToken(usuario)).thenReturn(expectedToken);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(expectedToken));

        // Verificar que os métodos foram chamados
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(usuario);
    }

    @Test
    void createUser_ValidUser_ShouldReturnCreatedUser() throws Exception {
        // Arrange
        Usuarios usuarioMock = mock(Usuarios.class);
        when(usuarioMock.getNome()).thenReturn("João Silva");
        when(usuarioMock.getCpf()).thenReturn("12345678900");
        when(usuarioMock.getSenha()).thenReturn("senha123");
        when(usuarioMock.getTelefone()).thenReturn("12345678900"); // Adiciona um telefone válido
        when(usuarioMock.getTipoDoUsuario()).thenReturn(TipoDoUsuario.MORADOR);
        when(usuarioMock.getBloco()).thenReturn('A');
        when(usuarioMock.getApartamento()).thenReturn(101);
        when(usuarioMock.getCondominio()).thenReturn(mock(Condominio.class)); //Mock para o condominio

        UsuarioDTO expectedDTO = new UsuarioDTO(
                1L,
                "João Silva",
                "12345678900",
                "MORADOR",
                'A',
                101,
                "Condomínio Teste",
                "Rua Teste, 123"
        );

        // Configurar o mock do service
        when(usuarioService.createUser(any(Usuarios.class))).thenReturn(expectedDTO);

        // Act & Assert
        mockMvc.perform(post("/auth/cadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioMock))) // Envia o mock
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"));

        verify(usuarioService).createUser(any(Usuarios.class));
    }

}