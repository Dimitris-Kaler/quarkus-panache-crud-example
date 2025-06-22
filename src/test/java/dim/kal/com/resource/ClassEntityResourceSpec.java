package dim.kal.com.resource;

import dim.kal.com.util.BaseTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ClassEntityResourceSpec extends BaseTest {

    @Test
    public void getAllClasses(){
        given()
                .when()
                .get("/classes")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()",is(5));
    }

    @Test
    public void testGetClassByIdExists() {
        int existingId = 1;

        given()
                .when()
                .get("/classes/{id}", existingId)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(existingId))
                .body("title", notNullValue());
    }

    @Test
    public void testGetClassByIdNotExists() {
        int nonExistingId = 999;

        given()
                .when()
                .get("/classes/{id}", nonExistingId)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", is("Class with ID 999 not found"));
    }


    @Test
    public void testGetByTeacherExists() {
        int teacherId = 1;

        given()
                .when()
                .get("/classes/teacher/{teacherId}", teacherId)
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testGetByTeacherNotExists() {
        int nonExistingTeacherId = 9999;

        given()
                .when()
                .get("/classes/teacher/{teacherId}", nonExistingTeacherId)
                .then()
                .log().all()
                .statusCode(200)
                .body("", hasSize(0));
    }

    @Test
    public void testGetByTitleExists() {
        String title = "Intro to Java";

        given()
                .queryParam("title", title)
                .when()
                .get("/classes/title")
                .then()
                .log().all()
                .statusCode(200)
                .body("title", is(title));
    }


    @Test
    public void testGetByTitleNotExists() {
        String title = "NoSuchTitle";

        given()
                .queryParam("title", title)
                .when()
                .get("/classes/title")
                .then()
                .log().all()
                .statusCode(404)
                .body("message",equalTo("Class with name 'NoSuchTitle' not found"));
    }

    @Test
    public void testCreateClass() {
        String json = """
        {
            "title": "New Class"
        }
        """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/classes")
                .then()
                .log().all()
                .statusCode(201)
                .body("title", is("New Class"));
    }


    @Test
    public void testCreateClassInvalid() {
        // Empty title triggers validation error
        String invalidJson = """
            {
              "title": ""
            }
            """;

        given()
                .contentType("application/json")
                .body(invalidJson)
                .when().post("/classes")
                .then()
                .statusCode(400)
                .body("message", containsString("Class title must not be null or empty"));
    }


    @Test
    public void testUpdateClassSuccess() {
        int existingId = 1;

        String jsonBody = """
            {
              "title": "Updated Class"
            }
            """;

        given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .put("/classes/{id}", existingId)
                .then()
                .statusCode(200);

        // Προαιρετικά, τσεκάρουμε ότι όντως ενημερώθηκε
        given()
                .when()
                .get("/classes/{id}",existingId)
                .then()
                .statusCode(200)
                .body("title",is("Updated Class"));
    }


    @Test
    public void testUpdateClassNotFound() {
        int existingId = 1000;

        String jsonBody = """
            {
              "title": "Updated Class"
            }
            """;

        given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .put("/classes/{id}", existingId)
                .then()
                .statusCode(404)
                .body("message",equalTo("Class with id '1000' not found"));
    }


    @Test
    public void testDeleteClass() {
        Long existingId = 1L;

        given()
                .when().delete("/classes/{id}", existingId)
                .then()
                .statusCode(204);


        given()
                .when()
                .get("/classes/{id}", existingId)
                .then()
                .statusCode(404)
                .body("message", is("Class with ID 1 not found"));

    }





}
