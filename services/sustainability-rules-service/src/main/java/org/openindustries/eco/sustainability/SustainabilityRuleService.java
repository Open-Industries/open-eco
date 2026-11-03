package org.openindustries.eco.sustainability;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SustainabilityRuleService {

    @Inject
    SustainabilityRuleRepository repository;

    public SustainabilityRule create(CreateSustainabilityRuleRequest request) {
        return repository.save(SustainabilityRule.create(request.name(), request.category(),
                request.reductionCapacityKg(), request.maxAuditFindings(), request.priority()));
    }

    public SustainabilityRule get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "SustainabilityRule not found: " + id));
    }

    public List<SustainabilityRule> list() {
        return repository.findAll();
    }

    public SustainabilityRule activate(UUID id) {
        SustainabilityRule rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.category());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.category());
        }
        for (SustainabilityRule rule : candidates) {
            if (rule.maxAuditFindings <= request.requiredMaxAuditFindings()
                    && rule.reductionCapacityKg >= request.requiredReductionCapacityKg()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.category() + " but none meets the requirement");
    }
}
