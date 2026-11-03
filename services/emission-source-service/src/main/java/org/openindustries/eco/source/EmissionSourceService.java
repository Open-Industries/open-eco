package org.openindustries.eco.source;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EmissionSourceService {

    @Inject
    EmissionSourceRepository repository;

    public EmissionSource create(CreateEmissionSourceRequest request) {
        return repository.save(EmissionSource.create(request.name(), request.sourceType(), request.facilityCode()));
    }

    public EmissionSource get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "EmissionSource not found: " + id));
    }

    public List<EmissionSource> list() {
        return repository.findAll();
    }

    public EmissionSource suspend(UUID id) {
        EmissionSource entity = get(id);
        if ("SUSPENDED".equals(entity.status)) {
            throw new DomainException(409, "EmissionSource already SUSPENDED");
        }
        entity.status = "SUSPENDED";
        return repository.save(entity);
    }

    public EmissionSource reactivate(UUID id) {
        EmissionSource entity = get(id);
        if ("ACTIVE".equals(entity.status)) {
            throw new DomainException(409, "EmissionSource already ACTIVE");
        }
        entity.status = "ACTIVE";
        return repository.save(entity);
    }
}
