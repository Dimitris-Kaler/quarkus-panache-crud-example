package dim.kal.com.repositories;

import dim.kal.com.models.Student;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dim.kal.com.models.Teacher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
public class TeacherRepositorySpec {
    @InjectSpy
    TeacherRepository teacherRepository;

    @Test
    @DisplayName("Find all teachers - return list")
    void testFindAllTeachers(){
        Teacher teacher1 = new Teacher();
        teacher1.setName("Teacher A");
        teacher1.setEmail("teach1@email.com");
        Teacher teacher2 = new Teacher();
        teacher1.setName("Teacher B");
        teacher1.setEmail("teach2@email.com");

        List<Teacher> mockList = List.of(teacher1,teacher2);

        when(teacherRepository.listAll()).thenReturn(mockList);

        List<Teacher> result = teacherRepository.findAllTeachers();

        assertEquals(2,result.size());
        verify(teacherRepository, times(1)).listAll();


    }

    @Test
    @DisplayName("Find Teacher by Id")
    void testFindById(){
        Long id = 1L;
        Teacher teacher = new Teacher();
        teacher.id = id;
        teacher.setName("John Doe");
        teacher.setEmail("john@example.com");

        PanacheQuery<Teacher> mockQuery = mock(PanacheQuery.class);
        when(teacherRepository.find("id", id)).thenReturn(mockQuery);
        when(mockQuery.firstResult()).thenReturn(teacher);

        Teacher result = teacherRepository.findById(id);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(teacherRepository, times(1)).find("id", id);
    }

    @Test
    @DisplayName("Find Teacher by Name")
    void testFindByName() {
        Long id= 2L;
        String name = "Takis Xantakias";
        Teacher teacher = new Teacher();
        teacher.id = id;
        teacher.setName(name);
        teacher.setEmail("xantaki@killer.com");


        PanacheQuery<Teacher> mockQuery = mock(PanacheQuery.class);
        when(teacherRepository.find("name",name)).thenReturn(mockQuery);
        when(mockQuery.firstResult()).thenReturn(teacher);

        Teacher result = teacherRepository.findByName(name);

        assertNotNull(result);
        assertEquals("Takis Xantakias",result.getName());
        verify(teacherRepository, times(1)).find("name", name);


    }

    @Test
    @DisplayName("Save teacher - success")
    @Transactional
    void testSave() {
        Teacher teacher = new Teacher();
        teacher.setName("New Teacher");
        teacher.setEmail("new@teacher.com");

        doNothing().when(teacherRepository).persist(teacher);

        teacherRepository.save(teacher);

        verify(teacherRepository, times(1)).persist(teacher);
    }


    @Test
    @DisplayName("Update teacher - success")
    @Transactional
    void testUpdate() {
        Long id = 1L;
        Teacher existing = new Teacher();
        existing.id=id;
        existing.setName("Old Name");
        existing.setEmail("old@email.com");

        Teacher updated = new Teacher();
        updated.setName("Updated Name");
        updated.setEmail("updated@email.com");

        when(teacherRepository.findById(id)).thenReturn(existing);

        teacherRepository.update(id, updated);

        assertEquals("Updated Name", existing.getName());
        assertEquals("updated@email.com", existing.getEmail());
        verify(teacherRepository, times(2)).findById(id);
    }


    @Test
    @DisplayName("Delete teacher by ID - success")
    @Transactional
    void testDelete() {
        Long id = 1L;

        // Επειδή η δική σου delete(Long) είναι void, μπορείς να κάνεις doNothing() σε αυτήν
       when(teacherRepository.delete("id",id)).thenReturn(1L);

        teacherRepository.delete(id);

        verify(teacherRepository, times(1)).delete("id",id);
    }




}
