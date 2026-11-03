# Emission Source Service

Bounded context: **Emission Sources** — Registry of emission sources per facility.

Alignment: ISO 14064-1 source identification. Lifecycle transitions take no body; secret identifiers,
where the domain has them, are masked at creation and never stored or returned in clear.
All data is synthetic.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/emission-sources` |
| Owns data | EmissionSource records |
| Publishes | `source.created`, lifecycle events (see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/emission-source-service quarkus:dev`.
