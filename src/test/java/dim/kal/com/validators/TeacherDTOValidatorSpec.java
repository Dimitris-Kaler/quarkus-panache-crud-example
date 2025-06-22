package dim.kal.com.validators;

import dim.kal.com.dtos.TeacherDTO;
import dim.kal.com.models.ApiException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TeacherDTOValidatorSpec {

    @Inject
    TeacherDTOValidator validator;

    @Test
    public void testValidate_ValidTeacher_NoException() {
        TeacherDTO dto = new TeacherDTO();
        dto.setName("Jane Smith");
        dto.setEmail("jane.smith@example.com");

        assertDoesNotThrow(() -> validator.validate(dto));
    }

    @Test
    public void testValidate_NullTeacher_ThrowsApiException() {
        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(null));
        assertEquals("Teacher cannot be null", ex.getMessage());
    }

    @Test
    public void testValidate_EmptyName_ThrowsApiException() {
        TeacherDTO dto = new TeacherDTO();
        dto.setName("  ");
        dto.setEmail("jane.smith@example.com");

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(dto));
        assertEquals("Teacher name is required", ex.getMessage());
    }

    @Test
    public void testValidate_NameTooShort_ThrowsApiException() {
        TeacherDTO dto = new TeacherDTO();
        dto.setName("A");
        dto.setEmail("jane.smith@example.com");

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(dto));
        assertEquals("The teacher's name must be between 2 and 50 characters", ex.getMessage());
    }

    @Test
    public void testValidate_EmptyEmail_ThrowsApiException() {
        TeacherDTO dto = new TeacherDTO();
        dto.setName("Jane Smith");
        dto.setEmail(" ");

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(dto));
        assertEquals("Teacher email is required", ex.getMessage());
    }

    @Test
    public void testValidate_InvalidEmailFormat_ThrowsApiException() {
        TeacherDTO dto = new TeacherDTO();
        dto.setName("Jane Smith");
        dto.setEmail("invalid-email");

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(dto));
        assertEquals("Invalid email format", ex.getMessage());
    }
}