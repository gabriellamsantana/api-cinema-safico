package br.com.cinemasafico.api.security;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ApiKeyService {

    private final Map<String, String> validKeys = new ConcurrentHashMap<>();

    public ApiKeyService() {
        validKeys.put("safico-key-2026", "admin@cinemasafico.com.br");
    }

    public String generateKey(String ownerEmail) {
        String newKey = "sfk-" + UUID.randomUUID();
        validKeys.put(newKey, ownerEmail);
        return newKey;
    }

    public boolean isValid(String apiKey) {
        return apiKey != null && validKeys.containsKey(apiKey);
    }
}
