package cnam.medical.pacs.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;


import cnam.medical.pacs.domain.dao.PatientRepo;
import cnam.medical.pacs.domain.model.Patient;
import cnam.medical.pacs.domain.model.Patient.Sex;


@Path("/patient")
public class PatientUI {

    private Logger LOGGER=Logger.getLogger(PatientUI.class);

    @Inject
    PatientRepo patientRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> getPatients(){

        LOGGER.info("Get List of Patients");
        return patientRepo.listAll();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/id/{id}")
    public Patient getPatient(@PathParam("id") Long id) {


        LOGGER.info("Get Patient by id");
        
        Patient patient=patientRepo.findById(id);

        return patientRepo.findById(id);//Response.status(Status.OK).entity(patient).build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/lastname/{lastname}")
    public List<Patient> getPatientByLastName(@PathParam("lastname") String lastname){

        LOGGER.info("Get Patients by lastname");
        return patientRepo.findByLastName(lastname);

    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/firstname/{firstname}")
    public List<Patient> getPatientByFirstName(@PathParam("firstname") String firstname){

        LOGGER.info("Get Patients by firstname");
        return patientRepo.findByFirstName(firstname);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sex/{sex}")
    public List<Patient> getPatientBySex(@PathParam("sex") Sex sex){

        LOGGER.info("Get Patients by sex");
        return patientRepo.findBySex(sex);

    }
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/birth/{birth}")
    public List<Patient> getPatientByBirth(@PathParam("birth") String birth){


        LOGGER.info("Get Patients by birthdate");

        //convert String to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        LocalDate localDate = LocalDate.parse(birth, formatter);
        
        return patientRepo.findByBirth(localDate);
        


    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response savePatient(Patient patient){

        LOGGER.info("Post Patient");
        patientRepo.persist(patient);
        return Response.status(Status.CREATED).entity(patient).build();

    }



    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @Transactional
    public Response deletePatient(@PathParam("id") Long id){

        LOGGER.info("Delete Patient");
        patientRepo.deleteById(id);
        return Response.status(Status.OK).build();

    }

 

    

}


