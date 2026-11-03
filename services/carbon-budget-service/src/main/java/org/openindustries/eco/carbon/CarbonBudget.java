package org.openindustries.eco.carbon;

import java.util.UUID;

/**
 * CarbonBudget aggregate — the guarded resource account of this core: a balance/capacity that
 * consumption operations draw from. Core invariant (ADR-0004): it NEVER goes negative;
 * an operation that would overdraw it is rejected and records nothing.
 */
public class CarbonBudget {
    public UUID id;
        public String facilityCode;
    public long budgetKgCo2e;
    public String status;

    public static CarbonBudget open(String facilityCode, long budgetKgCo2e) {
        CarbonBudget a = new CarbonBudget();
        a.id = UUID.randomUUID();
        a.facilityCode = facilityCode;
        a.budgetKgCo2e = budgetKgCo2e;
        a.status = "ACTIVE";
        return a;
    }
}
