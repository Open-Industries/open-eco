package org.openindustries.eco.offset;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class OffsetProjectRepository {
    private final ConcurrentHashMap<UUID, OffsetProject> store = new ConcurrentHashMap<>();

    public OffsetProject save(OffsetProject entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<OffsetProject> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<OffsetProject> findAll() {
        return List.copyOf(store.values());
    }
}
