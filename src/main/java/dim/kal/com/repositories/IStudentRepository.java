package dim.kal.com.repositories;

import dim.kal.com.models.Student;

import java.util.List;

public interface IStudentRepository {

    List<Student> findAllStudents();
    Student findById(Long id);
    Student findByEmail(String email);
    Student findByName(String name);
    void save(Student student);
    void update(Long id, Student updatedStudent);
    void delete(Long id);
}
