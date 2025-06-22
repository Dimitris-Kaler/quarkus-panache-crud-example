package dim.kal.com.mappers;

import dim.kal.com.dtos.ClassEntityDTO;
import dim.kal.com.dtos.StudentDTO;
import dim.kal.com.dtos.TeacherDTO;
import dim.kal.com.models.ClassEntity;
import dim.kal.com.models.Student;
import dim.kal.com.models.Teacher;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import io.quarkus.test.junit.QuarkusTest;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ClassEntityMapperSpec {
    @Mock
    TeacherMapper teacherMapper;

    @Mock
    StudentMapper studentMapper;

    @InjectMocks
    ClassEntityMapper classEntityMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToDTO_withTeacherAndStudents() {
        // Prepare test data
        Teacher teacher = new Teacher();
        teacher.id = 1L;
        teacher.setName("Mr. Smith");
        teacher.setEmail("smith@example.com");

        Student student1 = new Student();
        student1.id = 11L;
        student1.setName("Alice");
        student1.setEmail("alice@example.com");

        Student student2 = new Student();
        student2.id = 12L;
        student2.setName("Bob");
        student2.setEmail("bob@example.com");

        ClassEntity entity = new ClassEntity();
        entity.id = 100L;
        entity.setTitle("Math");
        entity.setTeacher(teacher);
        entity.setStudents(List.of(student1, student2));

        // Prepare mocks for teacherMapper & studentMapper
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(1L);
        teacherDTO.setName("Mr. Smith");
        teacherDTO.setEmail("smith@example.com");

        StudentDTO studentDTO1 = new StudentDTO();
        studentDTO1.setId(11L);
        studentDTO1.setName("Alice");
        studentDTO1.setEmail("alice@example.com");

        StudentDTO studentDTO2 = new StudentDTO();
        studentDTO2.setId(12L);
        studentDTO2.setName("Bob");
        studentDTO2.setEmail("bob@example.com");

        when(teacherMapper.toDTO(teacher)).thenReturn(teacherDTO);
        when(studentMapper.toDTO(student1)).thenReturn(studentDTO1);
        when(studentMapper.toDTO(student2)).thenReturn(studentDTO2);

        // Execute
        ClassEntityDTO dto = classEntityMapper.toDTO(entity);

        // Verify results
        assertNotNull(dto);
        assertEquals(100L, dto.getId());
        assertEquals("Math", dto.getTitle());
        assertNotNull(dto.getTeacher());
        assertEquals("Mr. Smith", dto.getTeacher().getName());

        assertNotNull(dto.getStudents());
        assertEquals(2, dto.getStudents().size());
        assertEquals("Alice", dto.getStudents().get(0).getName());
        assertEquals("Bob", dto.getStudents().get(1).getName());

        // Verify mocks called
        verify(teacherMapper).toDTO(teacher);
        verify(studentMapper).toDTO(student1);
        verify(studentMapper).toDTO(student2);
    }

    @Test
    public void testToEntity_withTeacherAndStudents() {
        // Prepare DTOs
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(1L);
        teacherDTO.setName("Mr. Smith");
        teacherDTO.setEmail("smith@example.com");

        StudentDTO studentDTO1 = new StudentDTO();
        studentDTO1.setId(11L);
        studentDTO1.setName("Alice");
        studentDTO1.setEmail("alice@example.com");

        StudentDTO studentDTO2 = new StudentDTO();
        studentDTO2.setId(12L);
        studentDTO2.setName("Bob");
        studentDTO2.setEmail("bob@example.com");

        ClassEntityDTO dto = new ClassEntityDTO();
        dto.setId(100L);
        dto.setTitle("Math");
        dto.setTeacher(teacherDTO);
        dto.setStudents(List.of(studentDTO1, studentDTO2));

        // Prepare mocks for teacherMapper & studentMapper
        Teacher teacher = new Teacher();
        teacher.id = 1L;
        teacher.setName("Mr. Smith");
        teacher.setEmail("smith@example.com");

        Student student1 = new Student();
        student1.id = 11L;
        student1.setName("Alice");
        student1.setEmail("alice@example.com");

        Student student2 = new Student();
        student2.id = 12L;
        student2.setName("Bob");
        student2.setEmail("bob@example.com");

        when(teacherMapper.toEntity(teacherDTO)).thenReturn(teacher);
        when(studentMapper.toEntity(studentDTO1)).thenReturn(student1);
        when(studentMapper.toEntity(studentDTO2)).thenReturn(student2);

        // Execute
        ClassEntity entity = classEntityMapper.toEntity(dto);

        // Verify results
        assertNotNull(entity);
        assertEquals("Math", entity.getTitle());
        assertNotNull(entity.getTeacher());
        assertEquals("Mr. Smith", entity.getTeacher().getName());

        assertNotNull(entity.getStudents());
        assertEquals(2, entity.getStudents().size());
        assertEquals("Alice", entity.getStudents().get(0).getName());
        assertEquals("Bob", entity.getStudents().get(1).getName());

        // Verify mocks called
        verify(teacherMapper).toEntity(teacherDTO);
        verify(studentMapper).toEntity(studentDTO1);
        verify(studentMapper).toEntity(studentDTO2);
    }

    @Test
    public void testToDTO_nullEntity() {
        assertNull(classEntityMapper.toDTO(null));
    }

    @Test
    public void testToEntity_nullDTO() {
        assertNull(classEntityMapper.toEntity(null));
    }
}
