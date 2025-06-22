package dim.kal.com.repositories;

import dim.kal.com.models.ClassEntity;
import dim.kal.com.models.Student;
import dim.kal.com.models.Teacher;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class ClassEntityRepositorySpec {

    @InjectSpy
    ClassEntityRepository repository;

    @Test
    @DisplayName("Find all classes - success")
    void testFindAllClasses() {
        ClassEntity c1 = new ClassEntity();
        c1.setTitle("Math");
        ClassEntity c2 = new ClassEntity();
        c2.setTitle("Science");

        List<ClassEntity> mockList = List.of(c1, c2);

        when(repository.listAll()).thenReturn(mockList);

        List<ClassEntity> result = repository.findAllClasses();

        assertEquals(2, result.size());
        verify(repository).listAll();
    }


    @Test
    @DisplayName("Find class by title - success")
    void testFindByTitle() {
        String title = "History";
        ClassEntity expected = new ClassEntity();
        expected.setTitle(title);

        PanacheQuery<ClassEntity> mockQuery = mock(PanacheQuery.class);
        when(repository.find("title", title)).thenReturn(mockQuery);
        when(mockQuery.firstResult()).thenReturn(expected);

        ClassEntity result = repository.findByTitle(title);

        assertNotNull(result);
        assertEquals("History", result.getTitle());
        verify(repository).find("title", title);
    }

    @Test
    @DisplayName("Find class by ID - success")
    void testFindClassById() {
        Long id = 5L;
        ClassEntity expected = new ClassEntity();
        expected.id = id;

        PanacheQuery<ClassEntity> mockQuery = mock(PanacheQuery.class);
        when(repository.find("id", id)).thenReturn(mockQuery);
        when(mockQuery.firstResult()).thenReturn(expected);

        ClassEntity result = repository.findClassById(id);

        assertNotNull(result);
        assertEquals(5L, result.id);
        verify(repository).find("id", id);
    }

    @Test
    @DisplayName("Find class by teacher ID - success")
    void testFindByTeacherId() {
        Long teacherId = 3L;
        ClassEntity c1 = new ClassEntity();
        ClassEntity c2 = new ClassEntity();

        List<ClassEntity> expected = List.of(c1, c2);

        when(repository.list("teacher.id", teacherId)).thenReturn(expected);

        List<ClassEntity> result = repository.findByTeacherId(teacherId);

        assertEquals(2, result.size());
        verify(repository).list("teacher.id", teacherId);
    }

    @Test
    @DisplayName("Save class - success")
    @Transactional
    void testSave() {
        ClassEntity c = new ClassEntity();
        c.setTitle("New Class");

        doNothing().when(repository).persist(c);

        repository.save(c);

        verify(repository).persist(c);
    }

    @Test
    @DisplayName("Update class - success")
    @Transactional
    void testUpdate() {
        Long id = 1L;

        Teacher teacher = new Teacher();
        teacher.setName("Professor");

        Student student = new Student();
        student.setName("Alex");

        ClassEntity existing = new ClassEntity();
        existing.id = id;
        existing.setTitle("Old Title");

        ClassEntity updated = new ClassEntity();
        updated.setTitle("Updated Title");
        updated.setTeacher(teacher);
        updated.setStudents(List.of(student));

        when(repository.findById(id)).thenReturn(existing);

        repository.update(id, updated);

        assertEquals("Updated Title", existing.getTitle());
        assertEquals("Professor", existing.getTeacher().getName());
        assertEquals(1, existing.getStudents().size());
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Delete class - success")
    @Transactional
    void testDelete() {
        Long testId = 9L;

        when(repository.delete("id", testId)).thenReturn(1L);

        repository.delete(testId);

        verify(repository).delete("id", testId);
    }
}
