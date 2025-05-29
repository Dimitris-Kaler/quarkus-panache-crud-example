package dim.kal.com.services;

import dim.kal.com.models.Teacher;

import java.util.List;

public interface ITeacherService {
    Teacher findById(Long id);
    List<Teacher> findAll();
    Teacher findByName(String name);
    void save(Teacher teacher);
    void update(Long id, Teacher teacher);
    void delete(Long id);
}
