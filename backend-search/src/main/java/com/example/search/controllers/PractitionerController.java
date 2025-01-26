package com.example.search.controllers;
import java.util.List;
import java.util.Objects;
import com.example.search.dto.requests.PractitionerAndDateRequest;
import com.example.search.dto.responses.UserResponse;
import com.example.search.interfaces.IPractitionerService;
import com.example.search.util.Result;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/practitioners")
public class PractitionerController {

    private final IPractitionerService practitionerService;


    public PractitionerController(IPractitionerService practitionerService) {
        this.practitionerService = Objects.requireNonNull(practitionerService, "practitionerService must not be null");
    }


    @GET
    @Path("/search")
    public Response searchPatientsByPractitioner(@QueryParam("id") int id) {

        var result = practitionerService.PatientsByPractitioner(id);
        if(!result.getSuccess()){
            return Response.status(Response.Status.BAD_REQUEST).entity(result.getMessage()).build();
        }
        if(result instanceof Result<?> res && res.getData() instanceof List<?> data && data.stream().allMatch(item -> item instanceof UserResponse)){
            return Response.ok(data).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Non expected return type").build();
    }

    @POST
    @Path("/searchPatientsByDay")
    public Response searchPatientsByPractitionerAndDate(PractitionerAndDateRequest request) {

        var result = practitionerService.PatientsByPractitionerAndDay(request);
        if(!result.getSuccess()){
            return Response.status(Response.Status.BAD_REQUEST).entity(result.getMessage()).build();
        }
        if(result instanceof Result<?> res && res.getData() instanceof List<?> data && data.stream().allMatch(item -> item instanceof UserResponse)){
            return Response.ok(data).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Non expected return type").build();
    }
}