package dim.kal.com.validators;


import dim.kal.com.dtos.ClassEntityDTO;
import dim.kal.com.models.ApiException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ClassEntityDTOValidatorSpec {

    @Inject
    ClassEntityDTOValidator validator;



    @Test
    public void testValidate_ValidDTO() {
        ClassEntityDTO dto = new ClassEntityDTO();
        dto.setTitle("Valid Title");
        assertDoesNotThrow(() -> validator.validate(dto));
    }

    @Test
    public void testValidate_NullDTO_ThrowsApiException(){
        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(null));
        assertEquals("ClassEntityDTO must not be null", ex.getMessage());
    }

    @Test
    public void testValidate_EmptyTitle_ThrowsApiException() {
        ClassEntityDTO dto = new ClassEntityDTO();
        dto.setTitle("  "); // κενό

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(dto));
        assertEquals("Class title must not be null or empty", ex.getMessage());
    }


    @Test
    public void testValidate_TitleTooShort_ThrowsApiException() {
        ClassEntityDTO dto = new ClassEntityDTO();
        dto.setTitle("A"); // λιγότερο από 2 χαρακτήρες

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(dto));
        assertEquals("Class title must be between 2 and 50 characters", ex.getMessage());
    }

    @Test
    public void testValidate_TitleTooLong_ThrowsApiException() {
        ClassEntityDTO dto = new ClassEntityDTO();
        dto.setTitle("A".repeat(51)); // πάνω από 50 χαρακτήρες

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(dto));
        assertEquals("Class title must be between 2 and 50 characters", ex.getMessage());
    }

}
