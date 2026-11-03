# Open Eco — Reference Environmental Management Core Platform

A **standard, generic environmental management and carbon accounting core platform** that any organization in this vertical
can deploy and adapt: built as **microservices and serverless functions** on
**Java 21 + Quarkus**, aligned with **The Twelve-Factor App**, documented under the ISO
architecture and quality standards, and aligned with the domain standards of the
vertical (see the traceability matrix). Everything is *aligned to* the cited standards —
**no certification, conformance or regulatory-compliance claim is made**. All data is
**synthetic**.

> Reference implementation: small, complete and runnable. The rules that matter in this
> domain are enforced in code and covered by tests — start with the invariant:
> a facility's emissions booked against its verified carbon budget never exceed that budget — an entry that would overrun it is rejected (422) and records nothing.

## Standards alignment

| Standard / framework | What it governs here | Where |
|---|---|---|
| ISO/IEC/IEEE **42010** | Architecture description | `docs/architecture/architecture-description.md` |
| ISO/IEC **25010** (SQuaRE) | Product quality model and scenarios | `docs/architecture/quality-attributes.md` |
| ISO/IEC/IEEE **15288 & 12207** | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC **27001 & 27002** | Information security | `docs/security/` |
| **The Twelve-Factor App** | Cloud-native methodology | `docs/architecture/twelve-factor.md` |
| ISO 14001 | Environmental management systems | Life-cycle and governance docs |
| ISO 14064-1 | GHG inventories (organization level) | Emission categories and budget accounting |
| ISO 14067 | Product carbon footprint | Boundary: product footprints consume this core's records |
| GHG Protocol | Scope 1/2/3 accounting | Emission entry categories |
| ISO 50001 | Energy management | Energy category rules |

Full traceability: `docs/compliance/standards-traceability.md`.

## The platform at a glance

### Microservices (Quarkus, `services/`)

| Service | Port | Alignment | Responsibility |
|---|---|---|---|
| `emission-source-service` | 8081 | ISO 14064-1 source identification | Registry of emission sources per facility. |
| `carbon-budget-service` | 8082 | GHG Protocol scope accounting; ISO 14064-1 inventories | Verified carbon budgets per facility and the emission entries booked against them. |
| `sustainability-rules-service` | 8083 | ISO 14001 operational control; ISO 50001 energy rules | Decision rules binding emission categories to reduction capacity and audit envelopes. |
| `offset-project-service` | 8084 | Voluntary carbon market practice (catalogue level) | Catalogue of offset projects under evaluation. |
| `notification-service` | 8085 | Platform support (shared pattern) | Channel-agnostic dispatch of domain notifications from events. |

### Serverless functions (Quarkus Funqy + Knative, `functions/`)

| Function | Trigger | Responsibility |
|---|---|---|
| `emission-risk-scorer` | Event / HTTP | Scores facility risk signals (intensity, leaks, reporting gaps) into a transparent score with rationale. |
| `carbon-compliance-evaluator` | Event / HTTP | Compares excess emissions and verified-share against targets; one finding per breach. |

## Design principles

1. **One bounded context per service**, identical hexagonal layout everywhere.
2. **The domain invariant is code, not prose** — a facility's emissions booked against its verified carbon budget never exceed that budget — an entry that would overrun it is rejected (422) and records nothing.
3. **Privacy by design** — Facility and emissions data is commercially and regulatorily sensitive: identifiers are internal, records are append-only evidence, and no personal data is required by any contract.
4. **Database per service, API-first (OpenAPI), events as CloudEvents.**
5. **Adapt by configuration** — rules, thresholds, tariffs/rates and providers are data
   and configuration, not code forks (see *Adaptation guide*).

## Quick start

Prerequisites: Java 21, Maven 3.9+, optionally Docker/Podman and Knative/OpenShift.

```bash
mvn verify                                    # build and test everything
mvn -pl services/emission-source-service quarkus:dev
docker compose up -d                          # local PostgreSQL, Kafka, Keycloak
```

## Deployment

- **Kubernetes / OpenShift**: `kubectl apply -k deploy/kubernetes/`.
- **Serverless**: Knative Services in `deploy/knative/` (scale to zero).
- **Local**: `docker-compose.yml` for dependencies; services run with `quarkus:dev`.

## Adaptation guide

| You want to… | Change only… |
|---|---|
| Adjust domain rules and thresholds | Rule data in the `sustainability-rules-service` context and function configuration |
| Change the guarded capacity semantics | The `carbon-budget-service` context (invariant stays: never negative) |
| Plug your IdP / providers | OIDC at the gateway; provider adapters at the boundary |
| Replace in-memory persistence | The repository port's JPA adapter on the service's own database (`DB_KIND`, `DB_URL`) |

## Documentation map

- Architecture (42010): `docs/architecture/` · Decisions: `docs/adr/`
- Domain standards: `docs/domain/domain-standards.md`
- Quality (25010), Security (27001/27002), Life cycle (15288/12207), API, Data, Operations: under `docs/`

## License

Apache-2.0 — see `LICENSE`.
