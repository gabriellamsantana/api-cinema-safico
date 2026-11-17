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
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Cinema Sáfico API",
                version = "2.0.0",
                description = """
                        API RESTful desenvolvida em Java e Spring Boot para catalogação de filmes, artistas, plataformas e avaliações do cinema sáfico.
                        
                        **Como testar no Swagger:**
                        1. **Gerar API Key:** Abra `POST /api/v1/auth/api-keys` e clique em **Execute** para gerar automaticamente uma chave única (`sfk-...`).
                        2. **Autenticar:** Clique no botão **Authorize** no topo direito, cole a chave gerada e confirme.
                        3. **Alternar Versões:** Use o menu **Select a definition** no topo direito para alternar entre **Versão 1.0 (v1)** e **Versão 2.0 (v2)**.
                        4. **Idempotência:** Em requisições `POST`, preencha o cabeçalho `X-Idempotency-Key` com um valor único por operação.
                        5. **Rate Limiting:** Limite de **10 requisições por minuto** por IP (retorna HTTP `429` com `Retry-After` ao exceder).
                        
                        **Precificação e Planos:**
                        | Plano | Limite de Requisições | Recursos Inclusos | Valor Mensal |
                        | :--- | :--- | :--- | :--- |
                        | **Free (Acadêmico)** | 10 req/min | Acesso completo à v1 e v2, HATEOAS e sandbox H2 | R$ 0,00 |
                        | **Creator** | 120 req/min | Consultas avançadas, webhooks e suporte prioritário | R$ 49,90 |
                        | **Enterprise** | Ilimitado | SLA de 99,9%, múltiplas chaves e infraestrutura dedicada | Sob consulta |
                        
                        **Termos de Uso:**
                        O acesso destina-se a fins acadêmicos, pesquisa e difusão cultural. É vedada a automação abusiva para contorno dos limites de taxa (`Rate Limit`) ou redistribuição comercial não autorizada.
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
                @Server(url = "/", description = "Servidor Atual (Render ou Local)")
        },
        security = @SecurityRequirement(name = "ApiKeyAuth")
)
@SecurityScheme(
        name = "ApiKeyAuth",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-API-Key",
        description = "Cole aqui a chave gerada automaticamente em POST /api/v1/auth/api-keys"
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiV1Group() {
        return GroupedOpenApi.builder()
                .group("v1")
                .displayName("Versão 1.0 (v1)")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public GroupedOpenApi apiV2Group() {
        return GroupedOpenApi.builder()
                .group("v2")
                .displayName("Versão 2.0 (v2)")
                .pathsToMatch("/api/v2/**", "/api/v1/auth/**")
                .build();
    }

    @Bean
    public OperationCustomizer idempotencyHeaderCustomizer() {
        return (operation, handlerMethod) -> {
            boolean isPost = handlerMethod.hasMethodAnnotation(PostMapping.class);
            boolean isAuthEndpoint = handlerMethod.getBeanType().getSimpleName().equals("ApiKeyController");
            if (isPost && !isAuthEndpoint) {
                Parameter idempotencyHeader = new Parameter()
                        .in("header")
                        .name("X-Idempotency-Key")
                        .description("Identificador único da operação para garantir idempotência")
                        .required(true)
                        .schema(new StringSchema()._default("req-001"));
                operation.addParametersItem(idempotencyHeader);
            }
            return operation;
        };
    }
}