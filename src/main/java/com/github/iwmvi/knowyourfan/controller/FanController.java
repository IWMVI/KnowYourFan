package com.github.iwmvi.knowyourfan.controller;

import com.github.iwmvi.knowyourfan.entity.Fan;
import com.github.iwmvi.knowyourfan.repository.FanRepository;
import com.github.iwmvi.knowyourfan.service.LinkValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/fans")
public class FanController {

    @Autowired
    private FanRepository repository;

    @Autowired
    private LinkValidationService linkValidationService;

    @PostMapping
    public ResponseEntity<String> signUp(
            @RequestParam("nome") String nome,
            @RequestParam("cpf") String cpf,
            @RequestParam("endereco") String endereco,
            @RequestParam("interesses") String interesses,
            @RequestParam(value = "documento", required = false) MultipartFile documento,
            @RequestParam(value = "linksEsports", required = false) List<String> linksEsports) {

        Fan fan = new Fan();
        fan.setNome(nome);
        fan.setCpf(cpf);
        fan.setEndereco(endereco);
        fan.setInteresses(interesses);

        if (documento != null && !documento.isEmpty()) {
            try {
                String documentoPath = "caminho/do/arquivo";
                fan.setDocumentoPath(documentoPath);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Erro ao processar o documento: " + e.getMessage());
            }
        }

        if (linksEsports != null) {
            fan.setLinksEsports(linksEsports);
            boolean algumLinkValido = linksEsports.stream().anyMatch(linkValidationService::validarLink);
            if (!algumLinkValido) {
                return ResponseEntity.badRequest().body("Nenhum link de e-sports válido foi fornecido.");
            }
        }

        repository.save(fan);
        return ResponseEntity.ok("Fã cadastrado(a) com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<Fan>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }
}
