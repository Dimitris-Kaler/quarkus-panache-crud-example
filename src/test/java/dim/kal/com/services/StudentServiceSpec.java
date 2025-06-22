package dim.kal.com.services;

import dim.kal.com.dtos.StudentDTO;
import dim.kal.com.mappers.StudentMapper;
import dim.kal.com.models.ApiException;
import dim.kal.com.models.Student;
import dim.kal.com.repositories.IStudentRepository;
import dim.kal.com.validators.StudentDTOValidator;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StudentServiceSpec {

    @Mock
    IStudentRepository studentRepository;

    @Mock
    StudentMapper studentMapper;

    @Mock
    StudentDTOValidator validator;

    @InjectMocks
    StudentService studentService;

    @BeforeEach
    void setUp() {
        // Ενεργοποιεί τα @Mock και κάνει inject τα dependencies στο studentService
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get all students - success")
    public void testGetAllStudents() {
        // Given
        Student student1 = new Student();
        student1.setName("Alex");
        Student student2 = new Student();
        student2.setName("Maria");

        StudentDTO dto1 = new StudentDTO();
        dto1.setName("Alex");
        StudentDTO dto2 = new StudentDTO();
        dto2.setName("Maria");

        when(studentRepository.findAllStudents()).thenReturn(List.of(student1, student2));
        when(studentMapper.toDTO(student1)).thenReturn(dto1);
        when(studentMapper.toDTO(student2)).thenReturn(dto2);

        // When
        List<StudentDTO> result = studentService.getAllStudents();

        // Then
        assertEquals(2, result.size());
        assertEquals("Alex", result.get(0).getName());
        assertEquals("Maria", result.get(1).getName());

        verify(studentRepository, times(1)).findAllStudents();
        verify(studentMapper, times(2)).toDTO(any());
    }

    @Test
    @DisplayName("Get student by id - success")
    public void testGetStudentById(){

        Student student1 = new Student();
        student1.setName("Alex");

        StudentDTO dto1 = new StudentDTO();
        dto1.setName("Alex");


        when(studentRepository.findById(1l)).thenReturn(student1);
        when(studentMapper.toDTO(student1)).thenReturn(dto1);

        //WHEN
        StudentDTO result = studentService.getStudentById(1L);

        assertEquals("Alex", result.getName());
        verify(studentRepository, times(1)).findById(1l);
        verify(studentMapper, times(1)).toDTO(any());

    }

    @Test
    @DisplayName("Get student by invalidId - faillure")
    public void testGetStudentByInvalidId(){

        Long invalidId = 10L;

        when(studentRepository.findById(invalidId)).thenReturn(null);


        ApiException exception = assertThrows(ApiException.class, () -> {
            studentService.getStudentById(invalidId);
        });

        assertEquals("Student with ID 10 not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
        verify(studentRepository, times(1)).findById(invalidId);
        verify(studentMapper, never()).toDTO(any());

    }

    @Test
    @DisplayName("Get student by email - success")
    public void testGetStudentByEmail(){

        String email = "alex@email.com";
        Student student1 = new Student();
        student1.setName("Alex");
        student1.setEmail(email);

        StudentDTO dto1 = new StudentDTO();
        dto1.setName("Alex");
        dto1.setEmail(email);


        when(studentRepository.findByEmail(email)).thenReturn(student1);
        when(studentMapper.toDTO(student1)).thenReturn(dto1);

        //WHEN
        StudentDTO result = studentService.getStudentByEmail(email);

        assertEquals("Alex", result.getName());
        assertEquals(email, result.getEmail());
        verify(studentRepository, times(1)).findByEmail(email);
        verify(studentMapper, times(1)).toDTO(any());

    }

    @Test
    @DisplayName("Get student by email - not found")
    public void testGetStudentByEmailNotFound() {
        String email = "missing@example.com";

        when(studentRepository.findByEmail(email)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            studentService.getStudentByEmail(email);
        });

        assertEquals("Student with email '" + email + "' not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
        verify(studentMapper, never()).toDTO(any());
    }


    @Test
    @DisplayName("Get student by name - success")
    public void testGetStudentByName(){

        String name = "Alex";
        Student student1 = new Student();
        student1.setName(name);
        student1.setEmail("someEmail@mail.com");

        StudentDTO dto1 = new StudentDTO();
        dto1.setName(name);
        dto1.setEmail("someEmail@mail.com");


        when(studentRepository.findByName(name)).thenReturn(student1);
        when(studentMapper.toDTO(student1)).thenReturn(dto1);

        //WHEN
        StudentDTO result = studentService.getStudentByName(name);

        assertEquals(name, result.getName());
        assertEquals("someEmail@mail.com", result.getEmail());
        verify(studentRepository, times(1)).findByName(name);
        verify(studentMapper, times(1)).toDTO(any());

    }


    @Test
    @DisplayName("Get student by name - not found")
    public void testGetStudentByNameNotFound() {
        String name = "Unknown";

        when(studentRepository.findByName(name)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            studentService.getStudentByName(name);
        });

        assertEquals("Student with name '" + name + "' not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
        verify(studentMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Create student - success")
    public void testCreateStudent() {
        StudentDTO dto = new StudentDTO();
        dto.setName("John");

        Student studentEntity = new Student();
        studentEntity.setName("John");

        when(studentMapper.toEntity(dto)).thenReturn(studentEntity);

        studentService.createStudent(dto);

        verify(validator).validate(dto);
        verify(studentRepository).save(studentEntity);
    }


    @Test
    @DisplayName("Update student - success")
    public void testUpdateStudent() {
        Long id = 1L;
        StudentDTO dto = new StudentDTO();
        dto.setName("Updated Name");

        Student existing = new Student();
        existing.id = id;
        existing.setName("Old Name");

        Student updatedEntity = new Student();
        updatedEntity.setName("Updated Name");

        when(studentRepository.findById(id)).thenReturn(existing);
        when(studentMapper.toEntity(dto)).thenReturn(updatedEntity);

        studentService.updateStudent(id, dto);

        verify(validator).validate(dto);
        verify(studentRepository).update(id, updatedEntity);
    }

    @Test
    @DisplayName("Update student - not found")
    public void testUpdateStudentNotFound() {
        Long id = 99L;
        StudentDTO dto = new StudentDTO();

        when(studentRepository.findById(id)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            studentService.updateStudent(id, dto);
        });

        assertEquals("Student with ID " + id + " not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
        verify(studentRepository, never()).update(anyLong(), any());
    }

    @Test
    @DisplayName("Delete student - success")
    public void testDeleteStudent() {
        Long id = 1L;
        Student existing = new Student();
        existing.id=id;

        when(studentRepository.findById(id)).thenReturn(existing);

        studentService.deleteStudent(id);

        verify(studentRepository).delete(id);
    }


    @Test
    @DisplayName("Delete student - not found")
    public void testDeleteStudentNotFound() {
        Long id = 77L;

        when(studentRepository.findById(id)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            studentService.deleteStudent(id);
        });

        assertEquals("Cannot delete — student with ID " + id + " not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
        verify(studentRepository, never()).delete(id);
    }











}
