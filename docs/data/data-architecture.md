# Data Architecture

Data view (VP-5) of the Architecture Description.

## 1. Ownership

| Data | Owner (only writer) | Store (reference → production) |
|---|---|---|
| Emission Sources records | emission-source-service | in-memory → PostgreSQL `source` |
| Carbon Budgets records | carbon-budget-service | in-memory → PostgreSQL `carbon` |
| Sustainability Rules records | sustainability-rules-service | in-memory → PostgreSQL `sustainability` |
| Offset Projects records | offset-project-service | in-memory → PostgreSQL `offset` |
| Notifications records | notification-service | in-memory → PostgreSQL `notification` |

In-memory adapters exist so the platform runs anywhere with zero setup; production
persistence is configuration (`DB_KIND`, `DB_URL`) plus the JPA adapter — the domain and
contracts never change (12-Factor IV).

## 2. Privacy and identifiers

Facility and emissions data is commercially and regulatorily sensitive: identifiers are internal, records are append-only evidence, and no personal data is required by any contract. Internal keys are UUIDs. All values in this repository are synthetic.

## 3. Consistency, evidence, retention

Strong within a context; eventual across contexts via CloudEvents with idempotent
consumers. Consumption and decision records are append-only evidence; corrections are
compensating records, never edits. Retention per the adopter's regulatory schedule;
personal data erasable per data-subject rights while evidential records are retained in
pseudonymized form (UUIDs only). Backups per service, encrypted, restore tested
(ISO 27002 A.8.13).
