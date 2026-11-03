package org.openindustries.eco.offset;

import java.util.UUID;

/** OffsetProject aggregate — Catalogue of offset projects under evaluation. */
public class OffsetProject {
    public UUID id;
        public String name;
        public String projectType;
        public long creditsTotal;
    public String status;

    public static OffsetProject create(String name, String projectType, long creditsTotal) {
        OffsetProject e = new OffsetProject();
        e.id = UUID.randomUUID();
        e.name = name;
        e.projectType = projectType;
        e.creditsTotal = creditsTotal;
        e.status = "DRAFT";
        return e;
    }
}
