package br.com.cinemasafico.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Cinema Sáfico API",
                version = "1.0.0",
                description = """
                        API RESTful desenvolvida com Spring Boot para catalogação, curadoria e avaliação de obras do cinema sáfico.
                        
                        **Recursos Disponíveis:**
                        * CRUD completo com paginação (`page`, `size`, `sort`) e consultas personalizadas (`/search`) para Filmes, Artistas, Avaliações, Plataformas de Streaming e Gêneros.
                        * Navegabilidade via hipermídia (**HATEOAS**) com links relacionais em todos os recursos.
                        * Validação de entrada com Bean Validation e respostas de erro padronizadas (`400` e `404`).
                        """,
                termsOfService = "https://github.com/gabriellamsantana/api-cinema-safico",
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
        }
)
public class SwaggerConfig {
}