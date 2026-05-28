package org.example.siimiltech.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service

public class EmailAuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String obtenerToken() {

        String url = "https://dev-sites.similtech.co/api-email/api/token";

        Map<String, String> body = new HashMap<>();
        body.put("username", "proceso_pruebas");
        body.put("password", "das487d32_*");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        return response.getBody().get("token").toString();
    }
}
