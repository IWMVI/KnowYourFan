package com.github.iwmvi.knowyourfan;

import com.github.iwmvi.knowyourfan.controller.FanController;
import com.github.iwmvi.knowyourfan.entity.Fan;
import com.github.iwmvi.knowyourfan.repository.FanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FanControllerTest {

    @Mock
    private FanRepository repository;

    @InjectMocks
    private FanController fanController;

    private Fan fan;
    private MultipartFile documento;

    @BeforeEach
    void setUp() {
        fan = new Fan();
        fan.setNome("João Silva");
        fan.setCpf("12345678901");
        fan.setEndereco("Rua Teste, 123");
        fan.setInteresses("Games, Esports");

        documento = new MockMultipartFile("documento", "test.pdf", "application/pdf", "conteúdo do arquivo".getBytes());
    }

    @Test
    void signUp_DeveRetornarSucessoQuandoDadosValidos() {
        // Arrange
        when(repository.save(any(Fan.class))).thenReturn(fan);

        // Act
        ResponseEntity<String> response = fanController.signUp(
                "João Silva",
                "12345678901",
                "Rua Teste, 123",
                "Games, Esports",
                null,
                Arrays.asList("https://steam.com/user123"));

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Fã cadastrado(a) com sucesso!", response.getBody());
        verify(repository, times(1)).save(any(Fan.class));
    }

    @Test
    void signUp_DeveRetornarErroQuandoLinksEsportsInvalidos() {
        // Act
        ResponseEntity<String> response = fanController.signUp(
                "João Silva",
                "12345678901",
                "Rua Teste, 123",
                "Games, Esports",
                null,
                Arrays.asList("https://invalido.com", "https://outrolink.com"));

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Nenhum link de e-sports válido foi fornecido.", response.getBody());
        verify(repository, never()).save(any(Fan.class));
    }

    @Test
    void signUp_DeveProcessarDocumentoQuandoFornecido() {
        // Arrange
        when(repository.save(any(Fan.class))).thenReturn(fan);

        // Act
        ResponseEntity<String> response = fanController.signUp(
                "João Silva",
                "12345678901",
                "Rua Teste, 123",
                "Games, Esports",
                documento,
                null);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(repository, times(1)).save(any(Fan.class));
    }

    @Test
    void listarTodos_DeveRetornarListaDeFans() {
        // Arrange
        List<Fan> fans = Arrays.asList(fan, new Fan());
        when(repository.findAll()).thenReturn(fans);

        // Act
        ResponseEntity<List<Fan>> response = fanController.listarTodos();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void listarTodos_DeveRetornarListaVaziaQuandoNaoHouverFans() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Fan>> response = fanController.listarTodos();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void linkEhValido_DeveRetornarTrueParaLinksValidos() {
        assertTrue(fanController.linkEhValido("https://steam.com/user123"));
        assertTrue(fanController.linkEhValido("https://www.faceit.com/player"));
        assertTrue(fanController.linkEhValido("https://hltv.org/news"));
    }

    @Test
    void linkEhValido_DeveRetornarFalseParaLinksInvalidos() {
        assertFalse(fanController.linkEhValido("https://google.com"));
        assertFalse(fanController.linkEhValido("https://twitter.com/user"));
        assertFalse(fanController.linkEhValido("https://invalido.com"));
    }
}