# Carbon Budget Service

Bounded context: **Carbon Budgets** — Verified carbon budgets per facility and the emission entries booked against them.

Alignment: GHG Protocol scope accounting; ISO 14064-1 inventories. Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/carbon-budgets` |
| Owns data | CarbonBudget accounts, EmissionEntry records |
| Publishes | `carbon.consumed`, `carbon.insufficient` |

Run locally: `mvn -pl services/carbon-budget-service quarkus:dev`.
