package dim.kal.com.resource;

import dim.kal.com.util.BaseTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class TeacherResourceSpec extends BaseTest {

    @Test
    public void testGetTeacherByIdExists() {
        int existingId = 1;

        given()
                .when()
                .get("/teachers/{id}", existingId)
                .then()
                .statusCode(200)
                .body("id", is(existingId))
                .body("name", equalTo("Joseph Stalin"))
                .body("email", notNullValue());
    }

    @Test
    public void testDeleteTeacherSuccess() {
        int existingId = 1;

        given()
                .when()
                .delete("/teachers/{id}", existingId)
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/teachers/{id}", existingId)
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void testGetAllTeachers() {
        given()
                .when()
                .get("/teachers")
                .then()
                .statusCode(200)
                .body("size()", is(10));
    }



    @Test
    public void testGetTeacherByIdNotExists() {
        int nonExistingId = 9999;

        given()
                .when()
                .get("/teachers/{id}", nonExistingId)
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetTeacherByNameExists() {
        String name = "Harry Truman";

        given()
                .queryParam("name", name)
                .when()
                .get("/teachers/name")
                .then()
                .statusCode(200)
                .body("name", is(name));
    }

    @Test
    public void testGetTeacherByNameNotExists() {
        String name = "NoSuchTeacher";

        given()
                .queryParam("name", name)
                .when()
                .get("/teachers/name")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetTeacherByEmail(){
        String existingEmail = "jfk@school.com";

        given()
                .when()
                .get("/teachers/email/{email}",existingEmail)
                .then()
                .log().all()
                .statusCode(200)
                .body("email", is(existingEmail))
                .body("name", is(equalTo("John F. Kennedy")));
    }





    @Test
    public void testCreateTeacherSuccess() {
        String json = """
        {
            "name": "Eleftherios Vanizelos",
            "email": "EthnikisAminis@metal.com"
        }
        """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/teachers")
                .then()
                .statusCode(201)
                .body("name", is("Eleftherios Vanizelos"))
                .body("email", is("EthnikisAminis@metal.com"));
    }

    @Test
    public void testGetTeacherByEmailNotExists() {
        String nonExistingEmail = "noone@nowhere.com"; // Email που δεν υπάρχει

        given()
                .when()
                .get("/teachers/email/{email}", nonExistingEmail)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", is("Teacher with email 'noone@nowhere.com' not found"));
    }

    @Test
    public void testCreateTeacherNullBody() {
        given()
                .contentType("application/json")
                .body("")
                .when()
                .post("/teachers")
                .then()
                .statusCode(400)
                .body("message", is("Teacher cannot be null"));
    }

    @Test
    public void testCreateTeacherEmptyName() {
        String json = """
        {
            "name": "",
            "email": "valid@teacher.com"
        }
        """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/teachers")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", is("Teacher name is required"));
    }

    @Test
    public void testCreateTeacherInvalidEmail() {
        String json = """
        {
            "name": "Valid Name",
            "email": "invalid-email"
        }
        """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/teachers")
                .then()
                .statusCode(400)
                .body("message", is("Invalid email format"));
    }

    @Test
    public void testUpdateTeacherSuccess() {
        int existingId = 1;

        String json = """
        {
            "name": "Updated Name",
            "email": "updated@teacher.com"
        }
        """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .put("/teachers/{id}", existingId)
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/teachers/{id}", existingId)
                .then()
                .statusCode(200)
                .body("name", is("Updated Name"))
                .body("email", is("updated@teacher.com"));
    }

    @Test
    public void testUpdateTeacherNotFound() {
        int nonExistingId = 9999;

        String json = """
        {
            "name": "Ghost Teacher",
            "email": "ghost@teacher.com"
        }
        """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .put("/teachers/{id}", nonExistingId)
                .then()
                .statusCode(404)
                .body("message", is("Teacher with ID 9999 not found"));
    }



    @Test
    public void testDeleteTeacherNotFound() {
        int nonExistingId = 12345;

        given()
                .when()
                .delete("/teachers/{id}", nonExistingId)
                .then()
                .statusCode(404)
                .body("message", is("Cannot delete — teacher with ID 12345 not found"));
    }
}
