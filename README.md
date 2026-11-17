
# 🎬 API Cinema Sáfico

Um catálogo digital de filmes, plataformas, artistas e avaliações focado em produções com temática sáfica. Esta API RESTful foi desenvolvida como projeto académico para demonstrar conhecimentos sólidos em back-end com o ecossistema Spring.


 
  ## Funcionalidades

- **CRUD Completo:** Gestão de Filmes, Plataformas, Artistas e Avaliações.
- **HATEOAS:** Implementação de hypermedia links para navegação direta pela API (entidade Filme).

- **Documentação Automática:** Interface interativa gerada com Swagger (OpenAPI) para teste de endpoints.
- **Validação de Dados:** Uso de anotações do Jakarta Validation (`@Valid`, `@NotBlank`) para garantir a integridade dos dados enviados no corpo das requisições.

## Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot (v4.1.1)
- **Banco de Dados:** H2 Database (In-Memory)
- **Persistência:** Spring Data JPA / Hibernate
- **Documentação:** Springdoc OpenAPI (Swagger UI)
- **Maturidade da API:** Spring HATEOAS
- **Gestor de Dependências:** Maven
##  Como executar o projeto

1. Clone o projeto:

git clone [https://github.com/SEU_USUARIO/api-cinema-safico.git](https://github.com/SEU_USUARIO/api-cinema-safico.git)

  2. Acesse a pasta do projeto e instale as dependências via Maven: mvn clean install

  3. Execute a aplicação Spring Boot: mvn spring-boot:ru
  
  A API estará disponível em http://localhost:8080.
##  Documentação (Swagger UI)

A documentação interativa completa dos endpoints, com os esquemas esperados e testes em tempo real, está disponível via Swagger.

Com a aplicação rodando, acesse no seu navegador:
http://localhost:8080/swagger-ui.html
