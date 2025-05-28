package dim.kal.com.services;

import dim.kal.com.models.Student;
import java.util.List;

public interface IStudentService {

     List<Student> getAllStudents();
     Student getStudentById(Long id);
     Student getStudentByEmail(String email);
     Student getStudentByName(String name);
     void createStudent(Student student);
    void updateStudent(Long id, Student student);
    void deleteStudent(Long id);


}
