package cnam.medical.pacs.api;

import java.util.List;

import javax.inject.Inject;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import cnam.medical.pacs.domain.dao.CompteRenduRepo;
import cnam.medical.pacs.domain.model.CompteRendu;
import cnam.medical.pacs.domain.model.CompteRendu.Etat;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

@Path("/compterendu")
@ValidateOnExecution
public class CompteRenduUI {

    private Logger LOGGER = Logger.getLogger(CompteRenduUI.class);

    @Inject
    CompteRenduRepo myRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompteRendu> getCompteRendus() {

        LOGGER.info("Get list of all CR");
        return myRepo.listAll();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveCompteRendu(@Valid CompteRendu compteRendu) {

        LOGGER.info("POST new CR");
        myRepo.persist(compteRendu);
        return Response.status(Status.CREATED).entity(compteRendu).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @Transactional
    public Response updateCompteRendu(@PathParam("id") Long id, CompteRendu compteRendu) {

        LOGGER.info("Update CR id: "+id);

        CompteRendu cr = myRepo.findById(id);
        cr.corp = compteRendu.corp;
        cr.etat = compteRendu.etat;
        cr.extension1 = compteRendu.extension1;
        cr.extension2 = compteRendu.extension2;
        cr.extension3 = compteRendu.extension3;
        cr.medecinLogin = compteRendu.medecinLogin;
        cr.secretaireLogin = compteRendu.secretaireLogin;
        cr.signature = compteRendu.signature;
        cr.titre = compteRendu.titre;

        cr.persist();

        return Response.status(Status.OK).entity(cr).build();

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @Transactional
    public Response deleteCompteRendu(@PathParam("id") Long id) {

        LOGGER.info("Delete CR id: "+id);

        myRepo.deleteById(id);

        return Response.status(Status.OK).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/id/{id}")
    public Response getCompteRenduById(@PathParam("id") Long id) {

        LOGGER.info("Get CR by id: "+id);

        CompteRendu cr = myRepo.findById(id);
        return Response.status(Status.OK).entity(cr).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/secretaireLogin/{secretaireLogin}")
    public List<CompteRendu> getCompteRenduBySecretaire(@PathParam("secretaireLogin") String secretaireLogin) {

        LOGGER.info("Get list of all CR by secretaireLogin: "+secretaireLogin);
        return myRepo.findBySecretaire(secretaireLogin);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/medecinLogin/{medecinLogin}")
    public List<CompteRendu> getCompteRenduByMedecin(@PathParam("medecinLogin") String medecinLogin) {

        LOGGER.info("Get list of all CR by medecinLogin: "+medecinLogin);
        return myRepo.findByMedecin(medecinLogin);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/etat/{etat}")
    public List<CompteRendu> getCompteRenduByEtat(@PathParam("etat") Etat etat) {

        LOGGER.info("Get list of all CR by etat: "+etat);
        return myRepo.findByEtat(etat);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/medecinLogin/{medecinLogin}/{etat}")
    public List<CompteRendu> getCompteRenduByMedecinANdEtat(@PathParam("medecinLogin") String medecinLogin,
            @PathParam("etat") Etat etat) {

        LOGGER.info("Get list of all CR by Medecin: "+medecinLogin+" and etat: "+ etat);
        return myRepo.findByMedecinAndEtat(medecinLogin, etat);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/secretaireLogin/{secretaireLogin}/{etat}")
    public List<CompteRendu> getCompteRenduBySecretaireAndEtat(@PathParam("secretaireLogin") String secretaireLogin,
            @PathParam("etat") Etat etat) {

        LOGGER.info("Get list of all CR by secretaire: "+secretaireLogin+" and etat: "+etat);
        return myRepo.findBySecretaireAndEtat(secretaireLogin, etat);

    }

}
