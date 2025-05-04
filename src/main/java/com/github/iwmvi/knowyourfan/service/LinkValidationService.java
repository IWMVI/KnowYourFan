package com.github.iwmvi.knowyourfan.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class LinkValidationService {

    public boolean validarLink(String urlPerfil) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String endpoint = "http://localhost:8000/validar-link?url=" + urlPerfil;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body().contains("true");
        } catch (Exception e) {
            return false;
        }
    }
}
