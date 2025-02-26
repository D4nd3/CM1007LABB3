package com.example.search.controllers;

import java.util.List;
import java.util.Objects;
import com.example.search.dto.responses.UserResponse;
import com.example.search.interfaces.IPatientService;
import com.example.search.util.Result;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/patients")
public class PatientController {

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = Objects.requireNonNull(patientService, "patientService must not be null");
    }

    @GET
    @Path("/searchName")
    public Response searchPatientsByName(@QueryParam("name") String name) {
        System.out.println("Searching for patients by name");
        var result = patientService.findPatientsByName(name);

        if(!result.getSuccess()){
            return Response.status(Response.Status.BAD_REQUEST).entity(result.getMessage()).build();
        }

        if(result instanceof Result<?> res && res.getData() instanceof List<?> data && data.stream().allMatch(item -> item instanceof UserResponse)){
            return Response.ok(data).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Non expected return type").build();
    }

    @GET
    @Path("/searchCondition")
    public Response searchPatientsByCondition(@QueryParam("condition") String condition) {
        var result = patientService.findPatientsByCondition(condition);

        if(!result.getSuccess()){
            return Response.status(Response.Status.BAD_REQUEST).entity(result.getMessage()).build();
        }

        if(result instanceof Result<?> res && res.getData() instanceof List<?> data && data.stream().allMatch(item -> item instanceof UserResponse)){
            return Response.ok(data).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Non expected return type").build();
    }

    @GET
    @Path("/searchStaff")
    public Response searchPatientsByStaffName(@QueryParam("name") String name) {
        var result = patientService.findPatientsByStaffName(name);

        if(!result.getSuccess()){
            return Response.status(Response.Status.BAD_REQUEST).entity(result.getMessage()).build();
        }

        if(result instanceof Result<?> res && res.getData() instanceof List<?> data && data.stream().allMatch(item -> item instanceof UserResponse)){
            return Response.ok(data).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Non expected return type").build();
    }
}