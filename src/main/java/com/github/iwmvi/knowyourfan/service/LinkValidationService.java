package com.github.iwmvi.knowyourfan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iwmvi.knowyourfan.dto.ValidacaoLinkResponse;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class LinkValidationService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean validarLink(String url) {
        try {
            String endpoint = "http://localhost:8000/validar-link?url=" +
                    URLEncoder.encode(url, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ValidacaoLinkResponse resultado = objectMapper.readValue(response.body(), ValidacaoLinkResponse.class);

            return resultado.isValido();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
