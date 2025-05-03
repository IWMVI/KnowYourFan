package com.github.iwmvi.knowyourfan.controller;

import com.github.iwmvi.knowyourfan.entity.Fan;
import com.github.iwmvi.knowyourfan.repository.FanRepository;
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

    @PostMapping
    public ResponseEntity<String> signUp(
            @RequestParam("nome") String nome,
            @RequestParam("cpf") String cpf,
            @RequestParam("endereco") String endereco,
            @RequestParam("interesses") String interesses,
            @RequestParam(value = "documento", required = false) MultipartFile documento) {

        // Verificação de campos obrigatórios
        if (nome == null || nome.isEmpty() ||
                cpf == null || cpf.isEmpty() ||
                interesses == null || interesses.isEmpty()) {
            return ResponseEntity.badRequest().body("Campos obrigatórios ausentes.");
        }

        // Criando o objeto Fan a partir dos dados recebidos
        Fan fan = new Fan();
        fan.setNome(nome);
        fan.setCpf(cpf);
        fan.setEndereco(endereco);
        fan.setInteresses(interesses);

        // Processando o documento (se houver)
        if (documento != null && !documento.isEmpty()) {
            try {
                // Salve o arquivo no local desejado (ou manipule como necessário)
                String documentoPath = "caminho/do/arquivo"; // Ajuste conforme a necessidade de salvar o arquivo
                fan.setDocumentoPath(documentoPath); // Atribuindo o caminho do documento
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Erro ao processar o documento: " + e.getMessage());
            }
        }

        // Salvando o fã no banco de dados
        repository.save(fan);

        // Retornando resposta de sucesso
        return ResponseEntity.ok("Fã cadastrado(a) com sucesso!");
    }


    // Método para listar todos os fãs cadastrados
    @GetMapping
    public ResponseEntity<List<Fan>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }
}
