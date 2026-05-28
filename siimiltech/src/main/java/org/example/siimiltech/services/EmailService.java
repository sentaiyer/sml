package org.example.siimiltech.services;

import org.example.siimiltech.dto.EmailRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EmailService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final EmailAuthService emailAuthService = new EmailAuthService();

    public String  enviarCorreo(String placa, String tipo, long tiempo, double valor) {

        String token = emailAuthService.obtenerToken();

        String url = "https://dev-sites.similtech.co/api-email/api/email/sendEmail";

        // ========================
        // 1. ARMAR JSON COMPLETO
        // ========================
        EmailRequest request = new EmailRequest();

        EmailRequest.ConfigParams config = new EmailRequest.ConfigParams();
        config.setIdUser("123");
        config.setIdMessage("456");

        EmailRequest.Receivers receivers = new EmailRequest.Receivers();
        receivers.setEmailOrigen("r.manchente@hotmail.com");
        receivers.setTo(List.of("r.manchente@hotmail.com"));
        receivers.setCopyTo(List.of("cc@correo.com"));
        receivers.setHiddenCopyTo(List.of("cco@correo.com"));

        EmailRequest.Email email = new EmailRequest.Email();
        email.setSubject("Hola desde Spring");
        email.setUrlHeader("https://header.png");
        email.setUrlFooter("https://footer.png");
        email.setMessage("Este es el mensaje");
        email.setUrl_files(List.of("https://file.pdf"));

        request.setConfigParams(config);
        request.setReceivers(receivers);
        request.setEmail(email);

        // ========================
        // 2. HEADERS (IMPORTANTE)
        // ========================
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token); // 👈 AQUÍ VA EL TOKEN

        HttpEntity<EmailRequest> entity = new HttpEntity<>(request, headers);

        // ========================
        // 3. LLAMADA API
        // ========================
        ResponseEntity<String> response =
                restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }
}
