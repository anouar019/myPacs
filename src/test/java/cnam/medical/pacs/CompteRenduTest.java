package cnam.medical.pacs;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.bootstrap.GenericBootstrap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cnam.medical.pacs.domain.dao.CompteRenduRepo;
import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

@QuarkusTest
public class CompteRenduTest {

    Integer myLong;

    @Inject
    CompteRenduRepo myRepo;

    @BeforeEach
    @Transactional
    void init() {

        // POST1
        given()
                .body("{\"secretaireLogin\": \"mySecretaire1\", \"medecinLogin\": \"myDoc1\", \"titre\": \"Titre du CR1\", \"corp\": \"En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche1\", \"signature\": \"myDocSignature1\",\"etat\": \"VALIDATED\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("/compterendu")
                .then()
                .statusCode(201);

        List myList = given().when().get("/compterendu").body().jsonPath().get("id");
        myLong = (Integer) myList.get(0);

        // POST2
        given()
                .body("{\"secretaireLogin\": \"mySecretaire2\", \"medecinLogin\": \"myDoc2\", \"titre\": \"Titre du CR2\", \"corp\": \"En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche2\", \"signature\": \"myDocSignature2\", \"etat\": \"WRITTEN\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("/compterendu")
                .then()
                .statusCode(201);

        // POST3
        given()
                .body("{\"secretaireLogin\": \"mySecretaire3\", \"medecinLogin\": \"myDoc3\", \"titre\": \"Titre du CR3\", \"corp\": \"En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche3\", \"signature\": \"myDocSignature3\", \"etat\": \"NULL\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("/compterendu")
                .then()
                .statusCode(201);

    }

    @AfterEach
    @Transactional
    void tearDown() {

        myRepo.deleteAll();
    }

    @Test
    public void testGetCompteRendu() {

        given()
                .when().get("/compterendu")
                .then()
                .statusCode(200)
                .body("size()", is(3));

    }

    @Test
    public void testPostCompteRendu() {

        given()
                .when().get("/compterendu")
                .then()
                .statusCode(200).body("$.size()", is(3),
                        "[0].secretaireLogin", is("mySecretaire1"),
                        "[0].medecinLogin", is("myDoc1"),
                        "[0].titre", is("Titre du CR1"),
                        "[0].corp",
                        is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche1"),
                        "[0].signature", is("myDocSignature1"),
                        "[0].etat", is("VALIDATED"),

                        "[1].secretaireLogin", is("mySecretaire2"),
                        "[1].medecinLogin", is("myDoc2"),
                        "[1].titre", is("Titre du CR2"),
                        "[1].corp",
                        is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche2"),
                        "[1].signature", is("myDocSignature2"),
                        "[1].etat", is("WRITTEN"),

                        "[2].secretaireLogin", is("mySecretaire3"),
                        "[2].medecinLogin", is("myDoc3"),
                        "[2].titre", is("Titre du CR3"),
                        "[2].corp",
                        is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche3"),
                        "[2].signature", is("myDocSignature3"),
                        "[2].etat", is("NULL"));

    }

    @Test
    public void testPutCompteRendu() {

        // Put
        given()
                .body("{\"secretaireLogin\": \"NewmySecretaire1\", \"medecinLogin\": \"NewmyDoc1\", \"titre\": \"NewTitre du CR1\", \"corp\": \"En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche1\", \"signature\": \"NewmyDocSignature1\",\"etat\": \"WRITTEN\"}")
                .header("Content-Type", "application/json")
                .when()
                .put("/compterendu/" + myLong)
                .then()
                .statusCode(200);

        // TEST Put result
        given()
                .when().get("/compterendu/id/" + myLong)
                .then()
                .statusCode(200).body("id", is(myLong),
                        "secretaireLogin", is("NewmySecretaire1"),
                        "medecinLogin", is("NewmyDoc1"),
                        "titre", is("NewTitre du CR1"),
                        "corp",
                        is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche1"),
                        "signature", is("NewmyDocSignature1"),
                        "etat", is("WRITTEN"));

    }

    @Test
    public void testdeleteCompteRendu() {

        given()
                .when().get("/compterendu")
                .then()
                .statusCode(200).body("$.size()", is(3),
                        "[0].secretaireLogin", is("mySecretaire1"),
                        "[0].medecinLogin", is("myDoc1"),
                        "[0].titre", is("Titre du CR1"),
                        "[0].corp",
                        is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche1"),
                        "[0].signature", is("myDocSignature1"),
                        "[0].etat", is("VALIDATED"));

        // DELETE
        given()
                .when().delete("/compterendu/" + myLong)
                .then()
                .statusCode(200);

        given()
                .when().get("/compterendu")
                .then()
                .statusCode(200).body("$.size()", is(2),
                        "[0].secretaireLogin", is("mySecretaire2"),
                        "[0].medecinLogin", is("myDoc2"),
                        "[0].titre", is("Titre du CR2"),
                        "[0].corp",
                        is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche2"),
                        "[0].signature", is("myDocSignature2"),
                        "[0].etat", is("WRITTEN"));

    }

    @Test
    public void testgetCompteRenduById() {

        given()
                .when().get("/compterendu/id/" + myLong)
                .then()
                .statusCode(200).body("id", is(myLong),
                        "secretaireLogin", is("mySecretaire1"),
                        "medecinLogin", is("myDoc1"),
                        "titre", is("Titre du CR1"),
                        "corp", is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche1"),
                        "signature", is("myDocSignature1"),
                        "etat", is("VALIDATED"));
    }


    @Test
    public void testgetCompteRenduBySecretaire(){

        given()
        .when().get("/compterendu/secretaireLogin/mySecretaire2")
        .then()
        .statusCode(200).body("$.size()", is(1), 
        "[0].secretaireLogin", is("mySecretaire2"),
        "[0].medecinLogin", is("myDoc2"),
        "[0].titre", is("Titre du CR2"),
        "[0].corp",
        is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche2"),
        "[0].signature", is("myDocSignature2"),
        "[0].etat", is("WRITTEN"));


        given()
        .when().get("/compterendu/secretaireLogin/mySecretaire1")
        .then()
        .statusCode(200).body("$.size()", is(1), 
        "[0].secretaireLogin", is("mySecretaire1"),
        "[0].medecinLogin", is("myDoc1"),
        "[0].titre", is("Titre du CR1"),
        "[0].corp", is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche1"),
        "[0].signature", is("myDocSignature1"),
        "[0].etat", is("VALIDATED"));

    }


    @Test
    public void testgetCompteRenduByMedecin(){


        given()
        .when().get("/compterendu/medecinLogin/myDoc2")
        .then()
        .statusCode(200).body("$.size()", is(1), 
        "[0].secretaireLogin", is("mySecretaire2"),
        "[0].medecinLogin", is("myDoc2"),
        "[0].titre", is("Titre du CR2"),
        "[0].corp",
        is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche2"),
        "[0].signature", is("myDocSignature2"),
        "[0].etat", is("WRITTEN"));


        given()
        .when().get("/compterendu/medecinLogin/myDoc1")
        .then()
        .statusCode(200).body("$.size()", is(1), 
        "[0].secretaireLogin", is("mySecretaire1"),
        "[0].medecinLogin", is("myDoc1"),
        "[0].titre", is("Titre du CR1"),
        "[0].corp", is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche1"),
        "[0].signature", is("myDocSignature1"),
        "[0].etat", is("VALIDATED"));


    }


    @Test
    public void testgetCompteRenduByEtat(){

        given()
        .when().get("/compterendu/etat/WRITTEN")
        .then()
        .statusCode(200).body("$.size()", is(1), 
        "[0].secretaireLogin", is("mySecretaire2"),
        "[0].medecinLogin", is("myDoc2"),
        "[0].titre", is("Titre du CR2"),
        "[0].corp",
        is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche2"),
        "[0].signature", is("myDocSignature2"),
        "[0].etat", is("WRITTEN"));


        given()
        .when().get("/compterendu/etat/VALIDATED")
        .then()
        .statusCode(200).body("$.size()", is(1), 
        "[0].secretaireLogin", is("mySecretaire1"),
        "[0].medecinLogin", is("myDoc1"),
        "[0].titre", is("Titre du CR1"),
        "[0].corp", is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche1"),
        "[0].signature", is("myDocSignature1"),
        "[0].etat", is("VALIDATED"));


    }


    @Test
    public void testgetCompteRenduByMedecinANdEtat(){

        given()
        .when().get("/compterendu/medecinLogin/myDoc2/WRITTEN")
        .then()
        .statusCode(200).body("$.size()", is(1), 
        "[0].secretaireLogin", is("mySecretaire2"),
        "[0].medecinLogin", is("myDoc2"),
        "[0].titre", is("Titre du CR2"),
        "[0].corp",
        is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche2"),
        "[0].signature", is("myDocSignature2"),
        "[0].etat", is("WRITTEN"));


        given()
        .when().get("/compterendu/medecinLogin/myDoc1/VALIDATED")
        .then()
        .statusCode(200).body("$.size()", is(1), 
        "[0].secretaireLogin", is("mySecretaire1"),
        "[0].medecinLogin", is("myDoc1"),
        "[0].titre", is("Titre du CR1"),
        "[0].corp", is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche1"),
        "[0].signature", is("myDocSignature1"),
        "[0].etat", is("VALIDATED"));

        given()
        .when().get("/compterendu/medecinLogin/myDoc1/WRITTEN")
        .then()
        .statusCode(200).body("$.size()", is(0));


        given()
        .when().get("/compterendu/medecinLogin/myDoc2/VALIDATED")
        .then()
        .statusCode(200).body("$.size()", is(0));

    }


    @Test
    public void testgetCompteRenduBySecretaireAndEtat(){

        given()
        .when().get("/compterendu/secretaireLogin/mySecretaire2/WRITTEN")
        .then()
        .statusCode(200).body("$.size()", is(1), 
        "[0].secretaireLogin", is("mySecretaire2"),
        "[0].medecinLogin", is("myDoc2"),
        "[0].titre", is("Titre du CR2"),
        "[0].corp",
        is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche2"),
        "[0].signature", is("myDocSignature2"),
        "[0].etat", is("WRITTEN"));


        given()
        .when().get("/compterendu/secretaireLogin/mySecretaire1/VALIDATED")
        .then()
        .statusCode(200).body("$.size()", is(1), 
        "[0].secretaireLogin", is("mySecretaire1"),
        "[0].medecinLogin", is("myDoc1"),
        "[0].titre", is("Titre du CR1"),
        "[0].corp", is("En effet, nous notons la présence de corps tumorale sur la partie inferieur du ganglion gauche1"),
        "[0].signature", is("myDocSignature1"),
        "[0].etat", is("VALIDATED"));

        given()
        .when().get("/compterendu/secretaireLogin/mySecretaire1/WRITTEN")
        .then()
        .statusCode(200).body("$.size()", is(0));


        given()
        .when().get("/compterendu/secretaireLogin/mySecretaire2/VALIDATED")
        .then()
        .statusCode(200).body("$.size()", is(0));

    }

}
