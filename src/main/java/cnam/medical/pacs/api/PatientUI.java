package cnam.medical.pacs.api;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cnam.medical.pacs.domain.model.Patient;

@Path("/patient")
public class PatientUI {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> getPatients(){

        return Patient.listAll();

    }

    

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response savePatient(Patient patient){

        patient.persist();
        return Response.status(Status.CREATED).entity(patient).build();

    }

}
