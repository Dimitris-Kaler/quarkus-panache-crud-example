package dim.kal.com.services;

import dim.kal.com.dtos.TeacherDTO;
import dim.kal.com.models.Teacher;

import java.util.List;

public interface ITeacherService {
    TeacherDTO findById(Long id);
    List<TeacherDTO> findAll();
    TeacherDTO findByName(String name);
    TeacherDTO getTeacherByEmail(String email);
    void save(TeacherDTO teacherDTO);
    void update(Long id, TeacherDTO teacherDTO);
    void delete(Long id);
}
