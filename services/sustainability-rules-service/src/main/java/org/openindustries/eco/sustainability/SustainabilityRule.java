package org.openindustries.eco.sustainability;

import java.util.UUID;

/**
 * SustainabilityRule aggregate — a decision rule of this core: for one category, it guarantees a
 * capacity (reductionCapacityKg) and a threshold (maxAuditFindings), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class SustainabilityRule {
    public UUID id;
    public String name;
    public String category;
    public long reductionCapacityKg;
    public int maxAuditFindings;
    public int priority;
    public String status;

    public static SustainabilityRule create(String name, String category, long reductionCapacityKg, int maxAuditFindings, int priority) {
        SustainabilityRule r = new SustainabilityRule();
        r.id = UUID.randomUUID();
        r.name = name;
        r.category = category;
        r.reductionCapacityKg = reductionCapacityKg;
        r.maxAuditFindings = maxAuditFindings;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
