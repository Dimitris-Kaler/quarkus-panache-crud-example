package dim.kal.com.services;

import dim.kal.com.dtos.StudentDTO;
import dim.kal.com.models.Student;
import java.util.List;

public interface IStudentService {

     List<StudentDTO> getAllStudents();
     StudentDTO getStudentById(Long id);
     StudentDTO getStudentByEmail(String email);
     StudentDTO getStudentByName(String name);
     void createStudent(StudentDTO studentDTO);
    void updateStudent(Long id, StudentDTO studentDTO);
    void deleteStudent(Long id);


}
