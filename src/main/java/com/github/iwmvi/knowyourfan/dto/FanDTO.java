package com.github.iwmvi.knowyourfan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class FanDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @Size(max = 255, message = "Endereço muito longo")
    private String endereco;

    private String interesses;
    private String atividades;
    private String compras;

    @Size(max = 255, message = "Link da rede social é muito longo")
    private List<String> redesSociais;
}
