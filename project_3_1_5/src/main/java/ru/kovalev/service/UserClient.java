package ru.kovalev.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ru.kovalev.model.UserDTO;

import java.util.List;

public class UserClient {
    private final RestTemplate restTemplate;
    private final String url = "http://94.198.50.185:7081/api/users";

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<UserDTO>> getAllUser() {
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
        });
        System.out.println(response.getHeaders());
        return response;
    }

    public String parseSessionId(String header) {
        String[] sessionId = header.split(";");
        return sessionId[0];
    }

    public String save(UserDTO userDTO, String sessionId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Cookie", sessionId);
            HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println("Ошибка: " + e.getStatusCode() + " — " + e.getResponseBodyAsString());
            return null;
        }
    }

    public String update(UserDTO userDTO, String sessionId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Cookie", sessionId);
            HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println("Ошибка: " + e.getStatusCode() + " — " + e.getResponseBodyAsString());
            return null;
        }
    }

    public String delete(Long id, String sessionId) {
        if (id != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", sessionId);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, entity, String.class);
            return response.getBody();
        } else {
            return null;
        }
    }
}
