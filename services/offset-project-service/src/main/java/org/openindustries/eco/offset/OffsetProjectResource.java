package org.openindustries.eco.offset;

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

@Path("/api/v1/offset-projects")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Offset Projects", description = "Catalogue of offset projects under evaluation.")
public class OffsetProjectResource {

    @Inject
    OffsetProjectService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a offsetproject")
    public Response create(@Valid CreateOffsetProjectRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<OffsetProject> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public OffsetProject get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/verify")
    @Operation(summary = "Verify (lifecycle transition, no body)")
    public OffsetProject verify(@PathParam("id") UUID id) {
        return service.verify(id);
    }
}
