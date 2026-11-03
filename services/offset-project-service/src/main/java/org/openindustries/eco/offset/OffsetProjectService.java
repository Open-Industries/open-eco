package org.openindustries.eco.offset;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OffsetProjectService {

    @Inject
    OffsetProjectRepository repository;

    public OffsetProject create(CreateOffsetProjectRequest request) {
        return repository.save(OffsetProject.create(request.name(), request.projectType(), request.creditsTotal()));
    }

    public OffsetProject get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "OffsetProject not found: " + id));
    }

    public List<OffsetProject> list() {
        return repository.findAll();
    }

    public OffsetProject verify(UUID id) {
        OffsetProject entity = get(id);
        if (!"DRAFT".equals(entity.status)) {
            throw new DomainException(409, "Only a DRAFT offsetproject can transition via verify");
        }
        entity.status = "VERIFIED";
        return repository.save(entity);
    }
}
