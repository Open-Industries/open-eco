# ADR-0004: The domain invariant is enforced in code

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

Every vertical has one rule whose violation destroys trust in the platform. Making it a tested code invariant — not documentation — is what makes this core a reference implementation.

## Decision

a facility's emissions booked against its verified carbon budget never exceed that budget — an entry that would overrun it is rejected (422) and records nothing. The enforcement lives in the operations context's single write path, and the behavior is covered by automated tests (over-draw rejected with 422; state unchanged).

## Consequences

Callers must treat 422 as a normal business outcome; the API standard documents it.
