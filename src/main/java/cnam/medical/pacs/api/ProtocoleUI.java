package cnam.medical.pacs.api;

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

import cnam.medical.pacs.domain.dao.ProtocoleRepo;
import cnam.medical.pacs.domain.model.Protocole;
import cnam.medical.pacs.domain.model.Protocole.Injection;
import cnam.medical.pacs.domain.model.Protocole.Membre;
import cnam.medical.pacs.domain.model.Protocole.Technique;
import cnam.medical.pacs.domain.model.Protocole.Type;

import org.jboss.logging.Logger;

@Path("/protocole")
public class ProtocoleUI {

    private Logger LOGGER = Logger.getLogger(ProtocoleUI.class);


    @Inject
    ProtocoleRepo myRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Protocole> getProtocoles() {

        LOGGER.info("Get List of Protocoles");
        return myRepo.listAll();

    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveProtocole(Protocole protocole){


        LOGGER.info("Post Protocole");
        myRepo.persist(protocole);
        return Response.status(Status.CREATED).entity(protocole).build();
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @Transactional
    public Response deleteProtocole(@PathParam("id") Long id) {



        LOGGER.info("Delete protocole by id: "+id);
        myRepo.deleteById(id);
        return Response.status(Status.OK).build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/id/{id}")
    public Response getProtocoleById(@PathParam("id") Long id) {


        LOGGER.info("Find protocole by id: "+id);
        Protocole protocole = myRepo.findById(id);
        return Response.status(Status.OK).entity(protocole).build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/technique/{technique}")
    public List<Protocole> getProtocoleByTechnique(@PathParam("technique") Technique technique) {


        LOGGER.info("Find protocole by technique: "+technique);
        return myRepo.findByTechnique(technique);

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/membre/{membre}")
    public List<Protocole> getProtocoleByMembre(@PathParam("membre") Membre membre) {

        LOGGER.info("Find protocole by membre: "+membre);
        return myRepo.findByMembre(membre);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/type/{type}")
    public List<Protocole> getProtocoleByType(@PathParam("type") Type type) {

        LOGGER.info("Find protocole by type: "+type);
        return myRepo.findByType(type);

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/injection/{injection}")
    public List<Protocole> getProtocoleByInjection(@PathParam("injection") Injection injection) {

        LOGGER.info("Find protocole by injection: "+injection);
        return myRepo.findByInjection(injection);

    }


}
