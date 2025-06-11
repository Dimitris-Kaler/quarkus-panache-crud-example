package dim.kal.com.services;

import dim.kal.com.dtos.ClassEntityDTO;
import dim.kal.com.models.ClassEntity;

import java.util.List;

public interface IClassEntityService {
    List<ClassEntityDTO> findAllClasses();
    ClassEntityDTO findClassById(Long id);
    ClassEntityDTO findClassEntityByTitle(String title);
    List<ClassEntityDTO> findByTeacherId(Long teacherId);
    void save(ClassEntityDTO classEntityDTO);
    void update(Long id, ClassEntityDTO updatedClass);
    void delete(Long id);
}
