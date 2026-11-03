package org.openindustries.eco.source;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/emission-sources")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Emission Sources", description = "Registry of emission sources per facility.")
public class EmissionSourceResource {

    @Inject
    EmissionSourceService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a emissionsource")
    public Response create(@Valid CreateEmissionSourceRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<EmissionSource> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public EmissionSource get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/suspend")
    @Operation(summary = "Suspend this emissionsource (lifecycle transition, no body)")
    public EmissionSource suspend(@PathParam("id") UUID id) {
        return service.suspend(id);
    }

    @POST
    @Path("/{id}/reactivate")
    @Operation(summary = "Reactivate this emissionsource (lifecycle transition, no body)")
    public EmissionSource reactivate(@PathParam("id") UUID id) {
        return service.reactivate(id);
    }
}
