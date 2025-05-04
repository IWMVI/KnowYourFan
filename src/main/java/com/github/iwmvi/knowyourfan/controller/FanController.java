package com.github.iwmvi.knowyourfan.controller;

import com.github.iwmvi.knowyourfan.entity.Fan;
import com.github.iwmvi.knowyourfan.repository.FanRepository;
import com.github.iwmvi.knowyourfan.service.LinkValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/fans")
public class FanController {

    @Autowired
    private FanRepository repository;

    @Autowired
    private LinkValidationService linkValidationService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String RECOMENDACAO_API_URL = "http://127.0.0.1:8001/recomendar";  // URL do microserviço FastAPI

    // Endpoint para cadastrar fã e obter recomendações
    @PostMapping
    public ResponseEntity<String> signUp(
            @RequestParam("nome") String nome,
            @RequestParam("cpf") String cpf,
            @RequestParam("endereco") String endereco,
            @RequestParam("interesses") String interesses,
            @RequestParam(value = "documento", required = false) MultipartFile documento,
            @RequestParam(value = "linksEsports", required = false) List<String> linksEsports) {

        // Criação de um novo fã
        Fan fan = new Fan();
        fan.setNome(nome);
        fan.setCpf(cpf);
        fan.setEndereco(endereco);
        fan.setInteresses(interesses);

        // Processando o arquivo do documento, se fornecido
        if (documento != null && !documento.isEmpty()) {
            try {
                String documentoPath = "caminho/do/arquivo";
                fan.setDocumentoPath(documentoPath);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Erro ao processar o documento: " + e.getMessage());
            }
        }

        // Validação dos links de e-sports, se fornecidos
        if (linksEsports != null) {
            fan.setLinksEsports(linksEsports);
            boolean algumLinkValido = linksEsports.stream().anyMatch(linkValidationService::validarLink);
            if (!algumLinkValido) {
                return ResponseEntity.badRequest().body("Nenhum link de e-sports válido foi fornecido.");
            }
        }

        // Salvando o fã no banco de dados
        repository.save(fan);

        // Integração com microserviço FastAPI para recomendações
        try {
            // Transforma a string "FPS,CSGO" em array JSON válido
            String[] interessesArray = interesses.split(",\\s*");
            StringBuilder jsonBuilder = new StringBuilder("{\"interesses\": [");
            for (int i = 0; i < interessesArray.length; i++) {
                jsonBuilder.append("\"").append(interessesArray[i]).append("\"");
                if (i < interessesArray.length - 1) jsonBuilder.append(",");
            }
            jsonBuilder.append("]}");
            String json = jsonBuilder.toString();

            // Criando a requisição HTTP para o microserviço
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(json, headers);

            // Enviando a requisição para o microserviço e recebendo a resposta
            ResponseEntity<String> response = restTemplate.postForEntity(RECOMENDACAO_API_URL, request, String.class);
            String recomendacoes = response.getBody();

            // Retornando a resposta para o frontend com as recomendações
            return ResponseEntity.ok("Fã cadastrado(a) com sucesso! Recomendações: " + recomendacoes);
        } catch (Exception e) {
            e.printStackTrace(); // log detalhado no console
            return ResponseEntity.status(500).body("Fã cadastrado(a), mas falha ao obter recomendações: " + e.getMessage());
        }
    }

    // Endpoint para listar todos os fãs cadastrados
    @GetMapping
    public ResponseEntity<List<Fan>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    // Endpoint para exportar dados dos fãs para um arquivo CSV
    @GetMapping("/exportar")
    public ResponseEntity<String> exportarCSV() {
        List<Fan> fans = repository.findAll();
        StringBuilder csv = new StringBuilder("nome,cpf,interesses\n");

        // Adicionando dados de cada fã ao CSV
        for (Fan fan : fans) {
            csv.append(fan.getNome()).append(",")
                    .append(fan.getCpf()).append(",")
                    .append(fan.getInteresses()).append("\n");
        }

        // Retornando o arquivo CSV para download
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fans.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv.toString());
    }

    // Função para validar se um link é válido
    public boolean linkEhValido(String url) {
        return linkValidationService.validarLink(url);
    }
}
