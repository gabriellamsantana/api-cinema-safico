package br.com.cinemasafico.api.controller;

import br.com.cinemasafico.api.security.ApiKeyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping({"/api/v1/api-keys", "/api/v2/api-keys"})
@Tag(name = "API Keys", description = "Endpoints para gerenciamento de chaves de API")
public class ApiKeyController {

    public record ApiKeyResponse(Integer usuarioId, String apiKey, String header) {}

    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @PostMapping("/usuario/{usuarioId}")
    @SecurityRequirements
    @Operation(
            summary = "Gerar uma nova chave de API para o usuário",
            description = "Recebe o ID numérico do usuário e gera automaticamente uma chave única para uso no cabeçalho X-API-Key."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chave gerada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID inválido ou cabeçalho X-Idempotency-Key ausente"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de idempotência (chave já processada)")
    })
    public ResponseEntity<ApiKeyResponse> gerarChave(
            @Parameter(description = "ID do usuário proprietário da chave", example = "1", required = true)
            @PathVariable Integer usuarioId) {

        if (usuarioId == null || usuarioId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do usuário deve ser um número positivo.");
        }

        String key = apiKeyService.generateKey(usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiKeyResponse(usuarioId, key, "X-API-Key"));
    }
}