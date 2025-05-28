package dim.kal.com.repositories;

import dim.kal.com.models.Teacher;

import java.util.List;

public interface ITeacherRepository {


    List<Teacher> findAllTeachers();
    Teacher findById(Long id);
    Teacher findByName(String name);
    void save(Teacher teacher);
    void update(Long id, Teacher updatedTeacher);
    void delete(Long id);
}
