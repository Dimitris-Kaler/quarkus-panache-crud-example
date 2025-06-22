package dim.kal.com.mappers;

import dim.kal.com.dtos.TeacherDTO;
import dim.kal.com.models.Teacher;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class TeacherMapperSpec {

    @Inject
    TeacherMapper mapper;


    @Test
    public void testToDTO() {
        Teacher entity = new Teacher();
        entity.id = 1L;
        entity.setName("John Doe");
        entity.setEmail("john@example.com");

        TeacherDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("John Doe", dto.getName());
        assertEquals("john@example.com", dto.getEmail());
    }

    @Test
    public void testToDTO_nullEntity() {
        assertNull(mapper.toDTO(null));
    }

    @Test
    public void testToEntity() {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(2L);
        dto.setName("Jane Doe");
        dto.setEmail("jane@example.com");

        Teacher entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Jane Doe", entity.getName());
        assertEquals("jane@example.com", entity.getEmail());
        // entity.id δεν αντιγράφεται όπως στο κώδικα
    }

    @Test
    public void testToEntity_nullDTO() {
        assertNull(mapper.toEntity(null));
    }
}

