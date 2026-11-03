package org.openindustries.eco.carbon;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CarbonService {

    @Inject
    CarbonRepository repository;

    public CarbonBudget open(OpenCarbonBudgetRequest request) {
        return repository.saveAccount(CarbonBudget.open(request.facilityCode(), request.budgetKgCo2e()));
    }

    public CarbonBudget get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "CarbonBudget not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public EmissionEntry consume(UUID accountId, CreateEmissionEntryRequest request) {
        CarbonBudget account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "CarbonBudget is not ACTIVE");
        }
        if (request.kgCo2e() > account.budgetKgCo2e) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.kgCo2e()
                    + " exceeds available " + account.budgetKgCo2e);
        }
        account.budgetKgCo2e -= request.kgCo2e();
        repository.saveAccount(account);
        return repository.saveRecord(EmissionEntry.of(accountId, request.scope(), request.kgCo2e(), account.budgetKgCo2e));
    }

    public List<EmissionEntry> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
