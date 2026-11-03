package org.openindustries.eco.sustainability;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class SustainabilityRuleRepository {
    private final ConcurrentHashMap<UUID, SustainabilityRule> store = new ConcurrentHashMap<>();

    public SustainabilityRule save(SustainabilityRule rule) {
        store.put(rule.id, rule);
        return rule;
    }

    public Optional<SustainabilityRule> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<SustainabilityRule> findAll() {
        return List.copyOf(store.values());
    }

    /** Active rules for a scope, highest priority (lowest number) first. */
    public List<SustainabilityRule> activeFor(String category) {
        return store.values().stream()
                .filter(r -> r.category.equals(category) && "ACTIVE".equals(r.status))
                .sorted(Comparator.comparingInt(r -> r.priority))
                .toList();
    }
}
