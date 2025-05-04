package com.github.iwmvi.knowyourfan.dto;

import lombok.Data;

@Data
public class ValidacaoLinkResponse {

    private boolean valido;
    private String motivo;

}
