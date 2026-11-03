# Sustainability Rules Service

Bounded context: **Sustainability Rules** — Decision rules binding emission categories to reduction capacity and audit envelopes.

Alignment: ISO 14001 operational control; ISO 50001 energy rules. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/sustainability-rules` |
| Owns data | SustainabilityRule rules |
| Publishes | `sustainability.activated` |

Run locally: `mvn -pl services/sustainability-rules-service quarkus:dev`.
