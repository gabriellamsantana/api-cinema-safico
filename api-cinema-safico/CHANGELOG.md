# Changelog

Todas as alterações notáveis neste projeto serão documentadas neste arquivo.
O formato baseia-se em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/) e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/) (`MAJOR.MINOR.PATCH`).

## [2.0.0] - 2026-11-17
### Motivo da Atualização (Major Update)
Evolução da arquitetura da API para incorporar segurança via chaves de acesso, controle de tráfego, idempotência e versionamento de contrato por URL sem quebrar integrações existentes.

### Adicionado
- **Versionamento de URL (`v1` e `v2`):** Migração das rotas para `/api/v1/...` e criação dos endpoints `/api/v2/filmes` com envelope de resposta enriquecido.
- **Autenticação via API Key (`X-API-Key`):** Filtro `ApiKeyFilter` e rota pública `POST /api/v1/auth/api-keys` para geração dinâmica de chaves.
- **Rate Limiting (Token Bucket):** Controle de taxa de 10 requisições/minuto por IP utilizando `Bucket4j`, retornando status `429 Too Many Requests` e cabeçalho `Retry-After`.
- **Idempotência:** Filtro `IdempotencyFilter` exigindo o cabeçalho `X-Idempotency-Key` em operações `POST` para prevenir cadastros duplicados.
- **Configuração de CORS (`CorsConfig`):** Liberação explícita de origens, métodos HTTP e cabeçalhos personalizados.
- **Governança no Swagger:** Inclusão de planos de precificação, termos de uso e autenticação interativa (`Authorize`).

## [1.1.0] - 2026-10-13
### Motivo da Atualização (New Features)
Adição de hipermídia e empacotamento para nuvem de forma retrocompatível.

### Adicionado
- Links **HATEOAS** (`self` e navegação entre coleções) nos recursos retornados.
- `Dockerfile` e `.dockerignore` para conteinerização da aplicação.

## [1.0.1] - 2026-10-13
### Motivo da Atualização (Bug Fixes & Patches)
Correções no tratamento de exceções e enriquecimento da documentação OpenAPI.

### Corrigido
- Padronização de respostas HTTP `400` e `404` via `GlobalExceptionHandler` e `ResponseStatusException`.
- Adição de descrições `@Operation`, `@ApiResponses` e `@Parameter` no Swagger UI.

## [1.0.0] - 2026-10-06
### Motivo da Atualização (Initial Release)
Lançamento inicial da API RESTful Cinema Sáfico.

### Adicionado
- CRUD completo paginado para Filmes, Artistas, Avaliações, Plataformas e Ficha Técnica com banco H2.