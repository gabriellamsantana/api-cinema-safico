package br.com.cinemasafico.api.security;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ApiKeyService {

    private final Map<String, Integer> validKeys = new ConcurrentHashMap<>();

    public String generateKey(Integer usuarioId) {
        String newKey = "sfk-" + UUID.randomUUID();
        validKeys.put(newKey, usuarioId);
        return newKey;
    }

    public boolean isValid(String apiKey) {
        return apiKey != null && validKeys.containsKey(apiKey);
    }
}