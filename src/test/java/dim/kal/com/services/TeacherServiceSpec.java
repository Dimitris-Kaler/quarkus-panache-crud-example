package dim.kal.com.services;







import dim.kal.com.dtos.TeacherDTO;
import dim.kal.com.models.ApiException;
import dim.kal.com.models.Teacher;
import dim.kal.com.repositories.ITeacherRepository;
import dim.kal.com.validators.TeacherDTOValidator;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import dim.kal.com.mappers.TeacherMapper;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TeacherServiceSpec {

    @Mock
    ITeacherRepository teacherRepository;


    @Mock
    TeacherMapper teacherMapper;

    @Mock
    TeacherDTOValidator validator;

    @InjectMocks
    TeacherService teacherService;

    @BeforeEach
    void setUp() {
        // Ενεργοποιεί τα @Mock και κάνει inject τα dependencies στο teacherService
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get all teachers - success")
    public void testFindAllteachers() {
        // Given
        Teacher teacher1 = new Teacher();
        teacher1.setName("Alex");
        Teacher teacher2 = new Teacher();
        teacher2.setName("Maria");

        TeacherDTO dto1 = new TeacherDTO();
        dto1.setName("Alex");
        TeacherDTO dto2 = new TeacherDTO();
        dto2.setName("Maria");

        when(teacherRepository.findAllTeachers()).thenReturn(List.of(teacher1, teacher2));
        when(teacherMapper.toDTO(teacher1)).thenReturn(dto1);
        when(teacherMapper.toDTO(teacher2)).thenReturn(dto2);

        // When
        List<TeacherDTO> result = teacherService.findAll();

        // Then
        assertEquals(2, result.size());
        assertEquals("Alex", result.get(0).getName());
        assertEquals("Maria", result.get(1).getName());

        verify(teacherRepository, times(1)).findAllTeachers();
        verify(teacherMapper, times(2)).toDTO(any());
    }

    @Test
    @DisplayName("Get teacher by id - success")
    public void testFindTeacherById(){

        Teacher teacher1 = new Teacher();
        teacher1.setName("Alex");

        TeacherDTO dto1 = new TeacherDTO();
        dto1.setName("Alex");


        when(teacherRepository.findById(1l)).thenReturn(teacher1);
        when(teacherMapper.toDTO(teacher1)).thenReturn(dto1);

        //WHEN
        TeacherDTO result = teacherService.findById(1L);

        assertEquals("Alex", result.getName());
        verify(teacherRepository, times(1)).findById(1l);
        verify(teacherMapper, times(1)).toDTO(any());

    }

    @Test
    @DisplayName("Get teacher by invalidId - faillure")
    public void testFindTeacherByInvalidId() {
        Long invalidId = 10L;
        when(teacherRepository.findById(invalidId)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            teacherService.findById(invalidId);
        });

        assertEquals("Teacher with ID 10 not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
        verify(teacherRepository, times(1)).findById(invalidId);
        verify(teacherMapper, never()).toDTO(any());

    }

    @Test
    @DisplayName("Get teacher by name - success")
    public void testFindTeacherByName(){

        String name = "Alex";
        Teacher teacher1 = new Teacher();
        teacher1.setName(name);
        teacher1.setEmail("someEmail@mail.com");

        TeacherDTO dto1 = new TeacherDTO();
        dto1.setName(name);
        dto1.setEmail("someEmail@mail.com");


        when(teacherRepository.findByName(name)).thenReturn(teacher1);
        when(teacherMapper.toDTO(teacher1)).thenReturn(dto1);

        //WHEN
        TeacherDTO result = teacherService.findByName(name);

        assertEquals(name, result.getName());
        assertEquals("someEmail@mail.com", result.getEmail());
        verify(teacherRepository, times(1)).findByName(name);
        verify(teacherMapper, times(1)).toDTO(any());

    }

    @Test
    @DisplayName("Get teacher by name - not found")
    public void testFindTeacherByNameNotFound() {
        String name = "Unknown";

        when(teacherRepository.findByName(name)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            teacherService.findByName(name);
        });

        assertEquals("Teacher with name '" + name + "' not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
        verify(teacherMapper, never()).toDTO(any());
    }



    @Test
    @DisplayName("Get teacher by email - success")
    public void testGetTeacherByEmail(){

        String email = "alex@email.com";
        Teacher teacher1 = new Teacher();
        teacher1.setName("Alex");
        teacher1.setEmail(email);

        TeacherDTO dto1 = new TeacherDTO();
        dto1.setName("Alex");
        dto1.setEmail(email);


        when(teacherRepository.findByEmail(email)).thenReturn(teacher1);
        when(teacherMapper.toDTO(teacher1)).thenReturn(dto1);

        //WHEN
        TeacherDTO result = teacherService.getTeacherByEmail(email);

        assertEquals("Alex", result.getName());
        assertEquals(email, result.getEmail());
        verify(teacherRepository, times(1)).findByEmail(email);
        verify(teacherMapper, times(1)).toDTO(any());

    }

    @Test
    @DisplayName("Get teacher by email - not found")
    public void testGetTeacherByEmailNotFound() {
        String email = "missing@example.com";

        when(teacherRepository.findByEmail(email)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            teacherService.getTeacherByEmail(email);
        });

        assertEquals("Teacher with email '" + email + "' not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
        verify(teacherMapper, never()).toDTO(any());
    }


    @Test
    @DisplayName("Create teacher - success")
    public void testCreateTeacher() {
        TeacherDTO dto = new TeacherDTO();
        dto.setName("John");

        Teacher teacherEntity = new Teacher();
        teacherEntity.setName("John");

        when(teacherMapper.toEntity(dto)).thenReturn(teacherEntity);

        teacherService.save(dto);

        verify(validator).validate(dto);
        verify(teacherRepository).save(teacherEntity);
    }


    @Test
    @DisplayName("Update teacher - success")
    public void testUpdateTeacher() {
        Long id = 1L;
        TeacherDTO dto = new TeacherDTO();
        dto.setName("Updated Name");

        Teacher existing = new Teacher();
        existing.id = id;
        existing.setName("Old Name");

        Teacher updatedEntity = new Teacher();
        updatedEntity.setName("Updated Name");

        when(teacherRepository.findById(id)).thenReturn(existing);
        when(teacherMapper.toEntity(dto)).thenReturn(updatedEntity);

        teacherService.update(id, dto);

        verify(validator).validate(dto);
        verify(teacherRepository).update(id, updatedEntity);
    }

    @Test
    @DisplayName("Update teacher - not found")
    public void testUpdateTeacherNotFound() {
        Long id = 99L;
        TeacherDTO dto = new TeacherDTO();

        when(teacherRepository.findById(id)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            teacherService.update(id, dto);
        });

        assertEquals("Teacher with ID " + id + " not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
        verify(teacherRepository, never()).update(anyLong(), any());
    }

    @Test
    @DisplayName("Delete teacher - success")
    public void testDeleteTeacher() {
        Long id = 1L;
        Teacher existing = new Teacher();
        existing.id=id;

        when(teacherRepository.findById(id)).thenReturn(existing);

        teacherService.delete(id);

        verify(teacherRepository).delete(id);
    }


    @Test
    @DisplayName("Delete teacher - not found")
    public void testDeleteTeacherNotFound() {
        Long id = 77L;

        when(teacherRepository.findById(id)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            teacherService.delete(id);
        });

        assertEquals("Cannot delete — teacher with ID " + id + " not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
        verify(teacherRepository, never()).delete(id);
    }



}
