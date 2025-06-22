package dim.kal.com.repositories;

import dim.kal.com.models.Student;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class StudentRepositorySpec {

    @InjectSpy
    StudentRepository studentRepository;

    @Test
    @DisplayName("Find student by ID -Success")
    void testFindById(){
        Long id = 1L;
        Student student = new Student();
        student.id = id;
        student.setName("John Doe");
        student.setEmail("john@example.com");

        PanacheQuery<Student> mockQuery = mock(PanacheQuery.class);
        when(studentRepository.find("id", id)).thenReturn(mockQuery);
        when(mockQuery.firstResult()).thenReturn(student);

        ///another otpion
//        PanacheQuery<Student> mockQuery = mock(PanacheQuery.class);
//        doReturn(mockQuery).when(studentRepository).find("id",id);
//        doReturn(student).when(mockQuery).firstResult();



        Student result = studentRepository.findById(id);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(studentRepository, times(1)).find("id", id);


    }



    @Test
    @DisplayName("Find student by email - success")
    void testFindByEmail() {
       Long id= 2L;
        String email = "jane@example.com";
        Student student = new Student();
        student.id = id;
        student.setName("Jane");
        student.setEmail(email);

        PanacheQuery<Student> mockQuery = mock(PanacheQuery.class);
        when(studentRepository.find("email", email)).thenReturn(mockQuery);
        when(mockQuery.firstResult()).thenReturn(student);

        Student result = studentRepository.findByEmail(email);

        assertNotNull(result);
        assertEquals("Jane", result.getName());
        verify(studentRepository, times(1)).find("email", email);
    }

    @Test
    @DisplayName("Save student - success")
    @Transactional
    void testSave() {
        Student student = new Student();
        student.setName("New Student");
        student.setEmail("new@student.com");

        doNothing().when(studentRepository).persist(student);

        studentRepository.save(student);

        verify(studentRepository, times(1)).persist(student);
    }

    @Test
    @DisplayName("Update student - success")
    @Transactional
    void testUpdate() {
        Long id = 1L;
        Student existing = new Student();
        existing.id=id;
        existing.setName("Old Name");
        existing.setEmail("old@email.com");

        Student updated = new Student();
        updated.setName("Updated Name");
        updated.setEmail("updated@email.com");

        when(studentRepository.findById(id)).thenReturn(existing);

        studentRepository.update(id, updated);

        assertEquals("Updated Name", existing.getName());
        assertEquals("updated@email.com", existing.getEmail());
        verify(studentRepository, times(2)).findById(id);
    }

    @Test
    @DisplayName("Delete student by ID - success")
    @Transactional
    void testDelete() {
        Long id = 1L;

        // Επειδή η δική σου delete(Long) είναι void, μπορείς να κάνεις doNothing() σε αυτήν
        when(studentRepository.delete("id",id)).thenReturn(1L);

        studentRepository.delete(id);

        verify(studentRepository, times(1)).delete("id",id);
    }

    @Test
    @DisplayName("Find all students - returns list")
    void testFindAllStudents() {
        Student student1 = new Student();
        student1.setName("A");
        Student student2 = new Student();
        student2.setName("B");

        List<Student> mockList = List.of(student1, student2);
        when(studentRepository.listAll()).thenReturn(mockList);

        List<Student> result = studentRepository.findAllStudents();

        assertEquals(2, result.size());
        verify(studentRepository, times(1)).listAll();
    }
}
