package dim.kal.com.validators;

import dim.kal.com.dtos.StudentDTO;
import dim.kal.com.models.ApiException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class StudentDTOValidatorSpec {

    @Inject
    StudentDTOValidator validator;

    @Test
    public void testValidate_ValidStudent_NoException() {
        StudentDTO dto = new StudentDTO();
        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");

        assertDoesNotThrow(() -> validator.validate(dto));
    }
    @Test
    public void testValidate_NullStudent_ThrowsApiException() {
        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(null));
        assertEquals("Student cannot be null", ex.getMessage());
    }

    @Test
    public void testValidate_EmptyName_ThrowsApiException() {
        StudentDTO dto = new StudentDTO();
        dto.setName("   ");
        dto.setEmail("john.doe@example.com");

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(dto));
        assertEquals("Student name is required", ex.getMessage());
    }

    @Test
    public void testValidate_NameTooShort_ThrowsApiException() {
        StudentDTO dto = new StudentDTO();
        dto.setName("A");
        dto.setEmail("john.doe@example.com");

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(dto));
        assertEquals("The Stedent's name must be between 2 and 50 characters", ex.getMessage());
    }

    @Test
    public void testValidate_EmptyEmail_ThrowsApiException() {
        StudentDTO dto = new StudentDTO();
        dto.setName("John Doe");
        dto.setEmail("  ");

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(dto));
        assertEquals("Student email is required", ex.getMessage());
    }

    @Test
    public void testValidate_InvalidEmailFormat_ThrowsApiException() {
        StudentDTO dto = new StudentDTO();
        dto.setName("John Doe");
        dto.setEmail("not-an-email");

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(dto));
        assertEquals("Invalid email format", ex.getMessage());
    }
}
