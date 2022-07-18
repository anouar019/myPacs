package cnam.medical.pacs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cnam.medical.pacs.domain.dao.PatientRepo;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import javax.transaction.Transactional;

@QuarkusTest
public class PatientTest {

        Integer myLong;

        @BeforeEach
        @Transactional
        void init() {

                PatientRepo myrepo = new PatientRepo();
                myrepo.deleteAll();

                // POST1
                given()
                                .body("{\"lastName\": \"Boufenara\", \"firstName\": \"Anouar\", \"birth\": \"1985-12-20\", \"sex\": \"MALE\"}")
                                .header("Content-Type", "application/json")
                                .when()
                                .post("/patient")
                                .then()
                                .statusCode(201);

                List myList = given().when().get("/patient").body().jsonPath().get("id");
                myLong = (Integer) myList.get(0);

                // POST2
                given()
                                .body("{\"lastName\": \"testFemale\", \"firstName\": \"testFemale\", \"birth\": \"1985-12-20\", \"sex\": \"FEMALE\"}")
                                .header("Content-Type", "application/json")
                                .when()
                                .post("/patient")
                                .then()
                                .statusCode(201);

                // POST3
                given()
                                .body("{\"lastName\": \"testMale\", \"firstName\": \"testMale\", \"birth\": \"2005-12-20\", \"sex\": \"MALE\"}")
                                .header("Content-Type", "application/json")
                                .when()
                                .post("/patient")
                                .then()
                                .statusCode(201);

        }

        @AfterEach
        @Transactional
        void tearDown() {

                PatientRepo myrepo = new PatientRepo();
                myrepo.deleteAll();

        }

        @Test
        public void testGetPatients() {

                given()
                                .when().get("/patient")
                                .then()
                                .statusCode(200);

        }

        @Test
        public void testPostPatient() {

                // VERIFICATION
                given()
                                .when().get("/patient")
                                .then()
                                .statusCode(200)
                                .body("$.size()", is(3),
                                                "[0].lastName", is("Boufenara"),
                                                "[0].firstName", is("Anouar"),
                                                "[0].birth", is("1985-12-20"),
                                                "[0].sex", is("MALE"),
                                                "[1].lastName", is("testFemale"),
                                                "[1].firstName", is("testFemale"),
                                                "[1].birth", is("1985-12-20"),
                                                "[1].sex", is("FEMALE"),
                                                "[2].lastName", is("testMale"),
                                                "[2].firstName", is("testMale"),
                                                "[2].birth", is("2005-12-20"),
                                                "[2].sex", is("MALE")

                                );
        }

        @Test
        public void testFindPatientByBirth() {

                // VERIFICATION1
                given()
                                .when().get("/patient/birth/1985-12-20")
                                .then()
                                .statusCode(200)
                                .body("$.size()", is(2),
                                                "[0].lastName", is("Boufenara"),
                                                "[0].firstName", is("Anouar"),
                                                "[0].birth", is("1985-12-20"),
                                                "[0].sex", is("MALE"),

                                                "[1].lastName", is("testFemale"),
                                                "[1].firstName", is("testFemale"),
                                                "[1].birth", is("1985-12-20"),
                                                "[1].sex", is("FEMALE")

                                );

        }

        @Test
        public void testFindPatientByID() {

                // VERIFICATION1
                given().when().get("/patient/id/" + myLong).then()
                                .statusCode(200)
                                .body("id", is(myLong),
                                                "lastName", is("Boufenara"),
                                                "firstName", is("Anouar"),
                                                "birth", is("1985-12-20"),
                                                "sex", is("MALE"));

        }

        @Test
        public void testFindPatientBySex() {

                // VERIFICATION1
                given()
                                .when().get("/patient/sex/MALE")
                                .then()
                                .statusCode(200)
                                .body("$.size()", is(2),
                                                "[0].lastName", is("Boufenara"),
                                                "[0].firstName", is("Anouar"),
                                                "[0].birth", is("1985-12-20"),
                                                "[0].sex", is("MALE"),

                                                "[1].lastName", is("testMale"),
                                                "[1].firstName", is("testMale"),
                                                "[1].birth", is("2005-12-20"),
                                                "[1].sex", is("MALE")

                                );

        }

        @Test
        public void testDeletePatient() {

                // VERIFICATION1
                given()
                                .when().get("/patient")
                                .then()
                                .statusCode(200)
                                .body("$.size()", is(3),
                                                "[0].lastName", is("Boufenara"),
                                                "[0].firstName", is("Anouar"),
                                                "[0].birth", is("1985-12-20"),
                                                "[0].sex", is("MALE"),

                                                "[1].lastName", is("testFemale"),
                                                "[1].firstName", is("testFemale"),
                                                "[1].birth", is("1985-12-20"),
                                                "[1].sex", is("FEMALE")

                                );

                given()
                                .when().delete("/patient/" + myLong)
                                .then()
                                .statusCode(200);

                given()
                                .when().get("/patient")
                                .then()
                                .statusCode(200)
                                .body("$.size()", is(2),

                                                "[0].lastName", is("testFemale"),
                                                "[0].firstName", is("testFemale"),
                                                "[0].birth", is("1985-12-20"),
                                                "[0].sex", is("FEMALE")

                                );

        }

}
