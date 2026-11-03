package org.openindustries.eco.carbon;

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

@Path("/api/v1/carbon-budgets")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Carbon Budgets", description = "Verified carbon budgets per facility and the emission entries booked against them.")
public class CarbonResource {

    @Inject
    CarbonService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Open a carbonbudget")
    public Response open(@Valid OpenCarbonBudgetRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.open(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public CarbonBudget get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/entries")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Draw from the account (rejects if it would overdraw, 422)")
    public EmissionEntry consume(@PathParam("id") UUID id, @Valid CreateEmissionEntryRequest request) {
        return service.consume(id, request);
    }

    @GET
    @Path("/{id}/entries")
    @Operation(summary = "List consumption records of an account")
    public List<EmissionEntry> records(@PathParam("id") UUID id) {
        return service.recordsOf(id);
    }
}
