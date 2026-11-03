# Offset Project Service

Bounded context: **Offset Projects** — Catalogue of offset projects under evaluation.

Alignment: Voluntary carbon market practice (catalogue level). Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/offset-projects` |
| Owns data | OffsetProject records |

Run locally: `mvn -pl services/offset-project-service quarkus:dev`.
