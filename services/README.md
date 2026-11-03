# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
Emission Sources → Carbon Budgets → Sustainability Rules → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `emission-source-service` | 8081 | ISO 14064-1 source identification |
| `carbon-budget-service` | 8082 | GHG Protocol scope accounting; ISO 14064-1 inventories |
| `sustainability-rules-service` | 8083 | ISO 14001 operational control; ISO 50001 energy rules |
| `offset-project-service` | 8084 | Voluntary carbon market practice (catalogue level) |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/emission-source-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
