package com.example.search;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/search")
public class SearchResource {

    @GET
    @Path("/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchPatients(
        @QueryParam("name") String name,
        @QueryParam("condition") String condition
    ) {
        return Response.ok("Här kommer patientdata").build();
    }

    @GET
    @Path("/doctors/{doctorId}/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientsByDoctor(@PathParam("doctorId") String doctorId) {
        return Response.ok("Här kommer patienter för läkare " + doctorId).build();
    }

    @GET
    @Path("/doctors/{doctorId}/encounters")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEncountersForDoctor(
        @PathParam("doctorId") String doctorId,
        @QueryParam("date") String date
    ) {
        return Response.ok("Här kommer encounters för läkare " + doctorId + " på " + date).build();
    }
}