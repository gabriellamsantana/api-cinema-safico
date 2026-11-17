package br.com.cinemasafico.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Cinema Sáfico API",
                version = "2.0.0",
                description = """
                        API RESTful desenvolvida com Spring Boot para catalogação de filmes, artistas, plataformas e avaliações do cinema sáfico.
                        
                        **Regras de Uso e Segurança:**
                        * **Autenticação (`X-API-Key`):** Gere sua chave em `POST /api/v1/auth/api-keys` (ou utilize a chave de demonstração `safico-key-2026`). Em seguida, clique no botão **Authorize** acima e informe a chave para liberar os demais endpoints.
                        * **Idempotência (`X-Idempotency-Key`):** Obrigatória em todas as requisições `POST` de cadastro. Envie um identificador único (ex: UUID) por operação para evitar registros duplicados.
                        * **Rate Limiting (Token Bucket):** Limite de **10 requisições por minuto** por endereço IP. Ao exceder, a API retorna status `429` com o cabeçalho `Retry-After`.
                        * **Versionamento (URL `v1` e `v2`):** Suporte a múltiplas versões de contrato via rota (`/api/v1/...` para contrato padrão e `/api/v2/...` para resposta enriquecida com metadados).
                        
                        **Precificação e Planos:**
                        | Plano | Limite de Requisições | Recursos Inclusos | Valor Mensal |
                        | :--- | :--- | :--- | :--- |
                        | **Free (Acadêmico)** | 10 req/min | Acesso completo à v1 e v2, HATEOAS e sandbox H2 | R$ 0,00 |
                        | **Creator** | 120 req/min | Consultas avançadas, webhooks e suporte prioritário | R$ 49,90 |
                        | **Enterprise** | Ilimitado | SLA de 99,9%, chaves múltiplas e ambiente dedicado | Sob consulta |
                        
                        **Termos de Uso:**
                        O acesso a esta API é destinado a fins educacionais e integração cultural. É vedado o uso de automações abusivas para contorno de limites de taxa (`Rate Limit`) ou redistribuição comercial não autorizada dos dados catalogados.
                        """,
                termsOfService = "https://github.com/gabriellamsantana/api-cinema-safico#termos-de-uso",
                contact = @Contact(
                        name = "Gabriella M Santana",
                        email = "contato@cinemasafico.com.br",
                        url = "https://github.com/gabriellamsantana/api-cinema-safico"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(url = "/", description = "Servidor Atual (Ambiente Local ou Nuvem Render)")
        },
        security = @SecurityRequirement(name = "ApiKeyAuth")
)
@SecurityScheme(
        name = "ApiKeyAuth",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-API-Key",
        description = "Informe sua chave de API no formato: safico-key-2026"
)
public class SwaggerConfig {
}