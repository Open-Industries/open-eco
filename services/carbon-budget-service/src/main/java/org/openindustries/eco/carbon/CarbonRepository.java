package org.openindustries.eco.carbon;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class CarbonRepository {
    private final ConcurrentHashMap<UUID, CarbonBudget> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<EmissionEntry> records = new CopyOnWriteArrayList<>();

    public CarbonBudget saveAccount(CarbonBudget account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<CarbonBudget> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public EmissionEntry saveRecord(EmissionEntry record) {
        records.add(record);
        return record;
    }

    public List<EmissionEntry> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
