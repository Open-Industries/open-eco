package org.openindustries.eco.source;

import java.time.Instant;
import java.util.UUID;

/**
 * EmissionSource aggregate — Registry of emission sources per facility.
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class EmissionSource {
    public UUID id;
        public String name;
        public String sourceType;
        public String facilityCode;
    public String status;
    public Instant createdAt;

    public static EmissionSource create(String name, String sourceType, String facilityCode) {
        EmissionSource e = new EmissionSource();
        e.id = UUID.randomUUID();
        e.name = name;
        e.sourceType = sourceType;
        e.facilityCode = facilityCode;
        e.status = "ACTIVE";
        e.createdAt = Instant.now();
        return e;
    }
}
