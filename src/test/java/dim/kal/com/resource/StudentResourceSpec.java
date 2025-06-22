package dim.kal.com.resource;

import dim.kal.com.util.BaseTest;
import dim.kal.com.services.StudentService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class StudentResourceSpec extends BaseTest{

    @Inject
    StudentService studentService;

    @Test
    public void testGetAllStudents() {
        given()
                .when()
                .get("/students")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(30))
                .body("[0].name",is(equalTo("Glen Benton")));
    }

    @Test
    public void testGetStudentsByIdExists(){
        int existingId =1;

        given()
                .when()
                .get("/students/{id}",existingId)
                .then()
                .log().all()
                .statusCode(200)
                .body("id",is(existingId))
                .body("name",is(equalTo("Glen Benton")))
                .body("email", notNullValue());
    }

    @Test
    public void testGetStudentByIdNotExists(){
        int nonExistingId=100;

        given()
                .when()
                .get("/students/{id}",nonExistingId)
                .then()
                .log().all()
                .statusCode(404)
                .body("message",is("Student with ID 100 not found"));
    }


    @Test
    public void testGetStudentByEmail(){
        String existingEmail = "glen@metal.com";

        given()
                .when()
                .get("/students/email/{email}",existingEmail)
                .then()
                .log().all()
                .statusCode(200)
                .body("email", is(existingEmail))
                .body("name", is(equalTo("Glen Benton")));
    }


    @Test
    public void testGetStudentByEmailNotExists() {
        String nonExistingEmail = "noone@nowhere.com"; // Email που δεν υπάρχει

        given()
                .when()
                .get("/students/email/{email}", nonExistingEmail)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", is("Student with email 'noone@nowhere.com' not found"));
    }


    @Test
    public void testGetStudentByNameExists() {
        String existingName = "Glen Benton"; // Υποθέτουμε ότι υπάρχει student με όνομα "Nikos"

        given()
                .when()
                .queryParam("name", existingName)
                .get("/students/name")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo(existingName))
                .body("email", notNullValue());
    }



    @Test
    public void testGetStudentByNameNotExists() {
        String nonExistingName = "NonExistentName123";

        given()
                .queryParam("name", nonExistingName)
                .when()
                .get("/students/name")
                .then()
                .log().all()
                .statusCode(404)
                .body("message",equalTo("Student with name 'NonExistentName123' not found"));
    }


    @Test
    public void testCreateStudentSuccess() {
        String newStudentJson = """
        {
            "name": "Dave Lombardo",
            "email": "slayer@drums.com"
        }
    """;

        given()
                .contentType("application/json")
                .body(newStudentJson)
                .when()
                .post("/students")
                .then()
                .statusCode(201)
                .body("name", is("Dave Lombardo"))
                .body("email", is("slayer@drums.com"));
    }

    @Test
    public void testCreateStudentNullBody() {
        given()
                .contentType("application/json")
                .body("") // άδειο body, που θα κάνει το dto null
                .when()
                .post("/students")
                .then()
                .statusCode(400)
                .body("message", is("Student cannot be null"));
    }

    @Test
    public void testCreateStudentEmptyName() {
        String json = """
        {
            "name": "",
            "email": "valid@example.com"
        }
    """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/students")
                .then()
                .statusCode(400)
                .body("message", is("Student name is required"));
    }


    @Test
    public void testCreateStudentShortName() {
        String json = """
        {
            "name": "A",
            "email": "valid@example.com"
        }
    """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/students")
                .then()
                .statusCode(400)
                .body("message", is("The Stedent's name must be between 2 and 50 characters"));
    }

    @Test
    public void testCreateStudentLongName() {
        String longName = "A".repeat(51); // 51 chars

        String json = String.format("""
        {
            "name": "%s",
            "email": "valid@example.com"
        }
    """, longName);

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/students")
                .then()
                .statusCode(400)
                .body("message", is("The Stedent's name must be between 2 and 50 characters"));
    }



    @Test
    public void testCreateStudentEmptyEmail() {
        String json = """
        {
            "name": "Valid Name",
            "email": ""
        }
    """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/students")
                .then()
                .statusCode(400)
                .body("message", is("Student email is required"));
    }



    @Test
    public void testCreateStudentInvalidEmail() {
        String json = """
        {
            "name": "Valid Name",
            "email": "not-an-email"
        }
    """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/students")
                .then()
                .statusCode(400)
                .body("message", is("Invalid email format"));
    }

    @Test
    public void testUpdateStudentSuccess() {
        int existingId = 1;

        String updatedJson = """
        {
            "name": "Sakis Tolis",
            "email": "rotting@example.com"
        }
    """;

        given()
                .contentType("application/json")
                .body(updatedJson)
                .when()
                .put("/students/{id}", existingId)
                .then()
                .statusCode(200);

        // Προαιρετικά, τσεκάρουμε ότι όντως ενημερώθηκε
        given()
                .when()
                .get("/students/{id}", existingId)
                .then()
                .statusCode(200)
                .body("name", is("Sakis Tolis"))
                .body("email", is("rotting@example.com"));
    }

    @Test
    public void testUpdateStudentNotFound() {
        int nonExistingId = 9999;

        String updatedJson = """
        {
            "name": "Ghost Student",
            "email": "ghost@student.com"
        }
    """;

        given()
                .contentType("application/json")
                .body(updatedJson)
                .when()
                .put("/students/{id}", nonExistingId)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", is("Student with ID 9999 not found"));
    }


    @Test
    public void testDeleteStudentSuccess() {
        int existingId = 1;

        given()
                .when()
                .delete("/students/{id}", existingId)
                .then()
                .statusCode(204);

        // Επιβεβαιώνουμε ότι δεν υπάρχει πια ο φοιτητής
        given()
                .when()
                .get("/students/{id}", existingId)
                .then()
                .statusCode(404);
    }

    @Test
    public void testDeleteStudentNotFound() {
        int nonExistingId = 100;

        given()
                .when()
                .delete("/students/{id}", nonExistingId)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", is("Cannot delete — student with ID 100 not found"));
    }



}
