package dim.kal.com.mappers;

import dim.kal.com.dtos.StudentDTO;
import dim.kal.com.models.Student;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class StudentMapperSpec {

    @Inject
    StudentMapper mapper;

    @Test
    public void testToDTO() {
        Student entity = new Student();
        entity.id = 10L;
        entity.setName("Alice");
        entity.setEmail("alice@example.com");

        StudentDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(10L, dto.getId());
        assertEquals("Alice", dto.getName());
        assertEquals("alice@example.com", dto.getEmail());
    }

    @Test
    public void testToDTO_nullEntity() {
        assertNull(mapper.toDTO(null));
    }

    @Test
    public void testToEntity() {
        StudentDTO dto = new StudentDTO();
        dto.setId(11L);
        dto.setName("Bob");
        dto.setEmail("bob@example.com");

        Student entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Bob", entity.getName());
        assertEquals("bob@example.com", entity.getEmail());
        // entity.id δεν αντιγράφεται όπως στο κώδικα
    }

    @Test
    public void testToEntity_nullDTO() {
        assertNull(mapper.toEntity(null));
    }
}
