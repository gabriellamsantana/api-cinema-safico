package br.com.cinemasafico.api.controller;

import br.com.cinemasafico.api.security.ApiKeyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/api-keys")
@Tag(name = "Autenticação (API Keys)", description = "Geração automática de chaves de acesso para consumo da API")
public class ApiKeyController {

    public record ApiKeyResponse(String email, String apiKey, String header) {}

    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Gerar nova API Key automaticamente", description = "Gera uma chave única para ser usada no botão Authorize (cabeçalho X-API-Key).")
    @ApiResponse(responseCode = "201", description = "Chave gerada com sucesso")
    public ApiKeyResponse gerarChave(
            @Parameter(description = "E-mail opcional do solicitante", example = "visitante@cinemasafico.com.br")
            @RequestParam(required = false, defaultValue = "visitante@cinemasafico.com.br") String email) {
        String key = apiKeyService.generateKey(email);
        return new ApiKeyResponse(email, key, "X-API-Key");
    }
}