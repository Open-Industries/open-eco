package org.openindustries.eco.carbon;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a CarbonBudget. */
public class EmissionEntry {
    public UUID id;
    public UUID accountId;
    public String scope;
    public long kgCo2e;
    public long remainingAfter;
    public Instant recordedAt;

    public static EmissionEntry of(UUID accountId, String scope, long kgCo2e, long remainingAfter) {
        EmissionEntry r = new EmissionEntry();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.scope = scope;
        r.kgCo2e = kgCo2e;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
