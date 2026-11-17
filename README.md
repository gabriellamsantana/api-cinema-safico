
# Cinema Sáfico API (v2.0.0)

API RESTful desenvolvida em Java e Spring Boot para catalogação, consulta e avaliação de obras do cinema sáfico, incluindo filmes, artistas, plataformas de exibição e críticas especializadas.

---

## Links de Acesso (Ambiente em Nuvem - Render)

* **Documentação Interativa (Swagger UI):** https://api-cinema-safico.onrender.com/swagger-ui/index.html
* **Especificação OpenAPI (JSON):** https://api-cinema-safico.onrender.com/v3/api-docs
* **Histórico de Versões (SemVer):** [CHANGELOG.md](./api-cinema-safico/CHANGELOG.md)

> **Nota:** Como a API está hospedada no plano gratuito do Render, a primeira requisição após um período de inatividade pode levar cerca de **50 segundos** para reativar a instância.

---

## Como Autenticar e Testar no Swagger

1. **Gerar Chave de Acesso (`X-API-Key`):** Expanda a rota pública `POST /api/v1/auth/api-keys` no Swagger UI e clique em **Execute**. O servidor gerará e exibirá automaticamente uma chave única no formato `sfk-<uuid>`.
2. **Autorizar Sessão:** Copie a chave gerada, clique no botão **Authorize** no topo direito do Swagger, cole no campo `X-API-Key` e confirme.
3. **Alternar entre Versões (`v1` e `v2`):** Utilize o menu suspenso **Select a definition** no canto superior direito do Swagger para alternar entre **Versão 1.0 (v1)** e **Versão 2.0 (v2)**.

---

## Regras de Segurança e Arquitetura

* **Autenticação (`ApiKeyFilter`):** Todas as rotas de recursos (`/api/v1/**` e `/api/v2/**`) exigem uma chave válida no cabeçalho `X-API-Key`, retornando **HTTP 401** quando ausente ou inválida.
* **Rate Limiting (`RateLimitFilter`):** Implementado com **Bucket4j** (algoritmo Token Bucket) com limite de **10 requisições por minuto** por IP. Ao exceder o limite, retorna **HTTP 429 (`Too Many Requests`)** com o cabeçalho `Retry-After`.
* **Idempotência (`IdempotencyFilter`):** Operações de criação (`POST`) exigem o cabeçalho `X-Idempotency-Key` com um identificador único por requisição, evitando duplicidade de registros em caso de reenvio.
* **CORS (`CorsConfig`):** Configurado para permitir requisições cross-origin com exposição dos cabeçalhos `X-Rate-Limit-Remaining` e `Retry-After`.
* **Versionamento & HATEOAS:** Versionamento via URL (`/api/v1` e `/api/v2`) seguindo **Versionamento Semântico (`MAJOR.MINOR.PATCH`)** e navegação hipermídia HATEOAS.

---

## Planos e Precificação

| Plano | Limite de Requisições | Recursos Inclusos | Valor Mensal |
| :--- | :--- | :--- | :--- |
| **Free (Acadêmico)** | 10 req/min | Acesso completo à v1 e v2, HATEOAS e sandbox H2 | R$ 0,00 |
| **Creator** | 120 req/min | Consultas avançadas, webhooks e suporte prioritário | R$ 49,90 |
| **Enterprise** | Ilimitado | SLA de 99,9%, múltiplas chaves e infraestrutura dedicada | Sob consulta |

## Termos de Uso
O uso desta API destina-se a fins acadêmicos, pesquisa e difusão cultural. É vedada a automação abusiva para contorno dos limites de taxa (`Rate Limit`) ou redistribuição comercial não autorizada do catálogo.

---

## Execução Local

```bash
cd api-cinema-safico
./mvnw spring-boot:run